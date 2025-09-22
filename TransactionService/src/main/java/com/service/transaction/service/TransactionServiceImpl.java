package com.service.transaction.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.service.transaction.binding.TransactionRequest;
import com.service.transaction.binding.TransferFund;
import com.service.transaction.client.AccountClient;
import com.service.transaction.entity.Transaction;
import com.service.transaction.exception.TransferFundException;
import com.service.transaction.repository.TransactionRepository;
import com.service.transaction.util.APiResponse;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountClient accountClient;

	@SuppressWarnings("unlikely-arg-type")
	@Override
	@Transactional
	public Transaction transferAmount(TransferFund transferFund) throws AccountNotFoundException {
		

		ResponseEntity<APiResponse<Double>> toAccBalance = accountClient.getBalance(transferFund.getToAcc());
		
		ResponseEntity<APiResponse<Double>> fromAccBalance = accountClient.getBalance(transferFund.getFromAcc());
	 
		if(toAccBalance.getBody().getData()==null || fromAccBalance.getBody().getData()==null) {

			throw new AccountNotFoundException("Account Not Found");
		}
		
		if (transferFund.getFromAcc().equals(transferFund.getToAcc())) {

			throw new TransferFundException("Transfer Fund Exception");

		}
		
		else{
			Transaction transaction = new Transaction();
		
			transaction.setTransactionRef(UUID.randomUUID().toString().substring(0, 10));
			transaction.setInitiatedAt(LocalDateTime.now());
			transaction.setStatus("PENDING");
			transactionRepository.save(transaction);

			ResponseEntity<APiResponse<Double>> accBalance = accountClient.getBalance(transferFund.getFromAcc());

			if (accBalance.getBody().getData() < transferFund.getAmount()) {
				transaction.setStatus("FAILED");
				transaction.setCompletedAt(LocalDateTime.now());
				return transactionRepository.save(transaction);
			}

			 TransactionRequest debit= new TransactionRequest();
			
			 debit.setAccountNumber(transferFund.getFromAcc());
			 debit.setAmount(transferFund.getAmount());
			 debit.setDescription(transferFund.getDescription());
			ResponseEntity<APiResponse<Boolean>> debitResponse = accountClient.debit(debit);
			
			if (debitResponse.getStatusCode().is5xxServerError()) {
				System.out.println("Executing Debit");
				throw new AccountNotFoundException("Account Not Found");
			}
			

			 TransactionRequest credit= new TransactionRequest();

			 credit.setAccountNumber(transferFund.getToAcc());
			 credit.setAmount(transferFund.getAmount());
			 credit.setDescription(transferFund.getDescription());
			 
			ResponseEntity<APiResponse<Boolean>> creditResponse = accountClient.credit(credit);

			if (creditResponse.getStatusCode().is5xxServerError()) {
				System.out.println("Credit Executing");
				throw new AccountNotFoundException("Account Not Found");
			}
			transaction.setFromAccountId(Long.valueOf(transferFund.getToAcc()));
			transaction.setToAccountId(Long.valueOf(transferFund.getToAcc()));
			transaction.setAmount(transferFund.getAmount());
			transaction.setDescription(transferFund.getDescription());
			transaction.setMode(transferFund.getMode());
			transaction.setStatus("COMPLETED");
			transaction.setCompletedAt(LocalDateTime.now());

			return transactionRepository.save(transaction);
		}

	}

	@Override
	public List<Transaction> getTransaction(String accountNumber) {
		Long accountId = Long.valueOf(accountNumber);

		List<Transaction> transactionList = transactionRepository.findByFromAccountIdOrToAccountId(accountId,
				accountId);

		return transactionList;
	}

}
