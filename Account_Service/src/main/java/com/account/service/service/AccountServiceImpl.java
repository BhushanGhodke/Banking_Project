package com.account.service.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account.service.binding.AccountBinding;
import com.account.service.binding.TransactionRequest;
import com.account.service.entity.Account;
import com.account.service.entity.AccountTransaction;
import com.account.service.entity.KycDetails;
import com.account.service.entity.User;
import com.account.service.exception.AccountExistsException;
import com.account.service.exception.InsufficientBalanceException;
import com.account.service.exception.KycUpdateException;
import com.account.service.feignclient.UserServiceClient;
import com.account.service.repo.AccountRepository;
import com.account.service.repo.AccountTransactionRepo;
import com.account.service.repo.KycDetailsRepo;
import com.account.service.util.APiResponse;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private KycDetailsRepo kycDetailsRepository;

	@Autowired
	private AccountTransactionRepo accountTransactionRepo;

	@Autowired
	private UserServiceClient userServiceClient;

	@Override
	public APiResponse<Account> createAccount(AccountBinding accountBinding) {

		Optional<Account> accountInfo = accountRepository.findByUserId(accountBinding.getUserId());

		APiResponse<Account> response = new APiResponse<>();

		if (accountInfo.isEmpty()) {

			Optional<KycDetails> kycDetails = kycDetailsRepository.getKycDetailsByUserId(accountBinding.getUserId());

			if (kycDetails.isPresent()) {

				Account account = new Account();
				account.setAccountNumber(generateAccountNumber());
				account.setAccountType(accountBinding.getAccountType());
				account.setBalance(0.0);
				account.setStatus("ACTIVE");
				account.setUserId(accountBinding.getUserId());
				account.setKycStatus(kycDetails.get().getStatus());
				Account createdAccount = accountRepository.save(account);
				kycDetails.get().setAccountId(createdAccount.getAccountId());
				kycDetailsRepository.save(kycDetails.get());
				response.setData(createdAccount);
				response.setErrorCode(201);
				response.setMsg("Account Created Successfully");
				return response;

			} else {
				response.setData(null);
				response.setErrorCode(500);
				response.setMsg(new KycUpdateException("Please Provide KYC Details").getMessage());
				return response;
			}

		} else {

			response.setData(null);
			response.setErrorCode(500);
			response.setMsg(new AccountExistsException("Account Exists Exception").getMessage());
			return response;
		}
	}

	private static final SecureRandom rnd = new SecureRandom();

	public static String generateAccountNumber() {

		StringBuilder sb = new StringBuilder(11);
		for (int i = 0; i < 11; i++) {
			int digit = rnd.nextInt(10); // 0..9
			sb.append(digit);
		}
		return sb.toString();
	}

	@Override
	public APiResponse<Account> getAccountInfoByUserId(Integer userId) {

		APiResponse<Account> response = new APiResponse<>();

		Optional<Account> accountInfo = accountRepository.findByUserId(userId);

		if (accountInfo.isPresent()) {
			response.setData(accountInfo.get());
			response.setErrorCode(200);
			response.setMsg("Account Info Fetched Successfully");
			return response;
		} else {
			response.setErrorCode(500);
			response.setMsg(new AccountNotFoundException("Account Not Found").getMessage());
			return response;
		}
	}

	@Override
	public APiResponse<User> getUserInfoById(Integer id) {

		APiResponse<User> userInfo = userServiceClient.getUserInfoById(id);

		return userInfo;
	}

	@Override
	public APiResponse<Boolean> getAccountOpenedOrNotByUserId(Integer userId) {

		APiResponse<Boolean> response = new APiResponse<>();
		Optional<Account> account = accountRepository.findByUserId(userId);

		if (account.isPresent()) {

			response.setData(true);
			response.setErrorCode(200);
			response.setMsg("Account Alerady Exists");

		}
		if (account.isEmpty()) {

			response.setData(false);
			response.setErrorCode(500);
			response.setMsg("Account Not Opened");

		}
		return response;

	}

	@Override
	public APiResponse<Double> getBalanceByAccountNumber(String accountNumber) throws AccountNotFoundException {

		APiResponse<Double> response = new APiResponse<>();
		Optional<Account> acc = accountRepository.findByAccountNumber(accountNumber);

		if(acc.isPresent()) {
			response.setData(acc.get().getBalance());
			response.setErrorCode(200);
			response.setMsg("Success");
			
		}else {
		    response.setData(null);
		    response.setErrorCode(500);
		    response.setMsg(new AccountNotFoundException("Account Not Found").getMessage());
		}
		

		return response;
	}

	@Override
	@Transactional
	public APiResponse<Boolean> credit(TransactionRequest txRequest) throws AccountNotFoundException {
		APiResponse<Boolean> response = new APiResponse<>();

		Optional<Account> account = accountRepository.findByAccountNumber(txRequest.getAccountNumber());

		if (account.isEmpty()) {
			
			throw new AccountNotFoundException("Account Not Found");
		}
		
		 else {
		
			account.get().setBalance(account.get().getBalance() + txRequest.getAmount());
			
			Account save = accountRepository.save(account.get());
			
			
			AccountTransaction accountTransaction= new AccountTransaction();
			accountTransaction.setAccountNumber(save.getAccountNumber());
			accountTransaction.setAmount(txRequest.getAmount());
			accountTransaction.setTransactionType("CREDIT");
			accountTransaction.setDescription(txRequest.getDescription());
			accountTransaction.setTransactionDate(LocalDateTime.now());
			accountTransaction.setBalance(save.getBalance());	
			accountTransactionRepo.save(accountTransaction);
			
			response.setData(save.getAccountId()!=null);
			
			response.setErrorCode(200);
			
			response.setMsg("Success");
			
			
		}

		return response;

	}

	@Override	
	@Transactional
	public APiResponse<Boolean> debit(TransactionRequest txRequest) throws AccountNotFoundException {

		APiResponse<Boolean> response = new APiResponse<>();

		Optional<Account> account = accountRepository.findByAccountNumber(txRequest.getAccountNumber());

		if (account.isEmpty()) {
			
			throw new AccountNotFoundException("Account Not Found");
		}
		
		if (account.get().getBalance() < txRequest.getAmount()) {
		
			throw new InsufficientBalanceException("Insufficient Fund ");
		
		} else {
		
			account.get().setBalance(account.get().getBalance() - txRequest.getAmount());
			
			Account save = accountRepository.save(account.get());
			
			AccountTransaction accountTransaction= new AccountTransaction();
			accountTransaction.setAccountNumber(save.getAccountNumber());
			
			accountTransaction.setAmount(txRequest.getAmount());
			accountTransaction.setTransactionType("DEBIT");
			accountTransaction.setDescription(txRequest.getDescription());
			accountTransaction.setTransactionDate(LocalDateTime.now());
			accountTransaction.setBalance(save.getBalance());
			accountTransactionRepo.save(accountTransaction);
			
			response.setData(save.getAccountId()!=null);
			
			response.setErrorCode(200);
			
			response.setMsg("Success");
		}

		return response;
	}

}
