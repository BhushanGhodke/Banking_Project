package com.account.service.service;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.service.entity.AccountTransaction;
import com.account.service.repo.AccountTransactionRepo;
import com.account.service.util.APiResponse;
@Service
public class TransactionServiceImpl implements TransactionService{

	
	@Autowired
	private AccountTransactionRepo accountTransactionRepo;
	
	@Override
	public APiResponse<List<AccountTransaction>> getTransactionDetailsByAccountNumber(String accountNumber) {

		APiResponse<List<AccountTransaction>> response= new APiResponse<>();
		
		
		List<AccountTransaction> transactionList = accountTransactionRepo.findByAccountNumber(accountNumber);
		
		List<AccountTransaction> descendingOrderList = transactionList.stream().sorted((x,y)->y.getAccTransId().compareTo(x.getAccTransId())).collect(Collectors.toList());
		
		 if(transactionList.isEmpty()) {
		
			 response.setMsg("Account Transaction Not Found");
			 response.setErrorCode(500);
			 return response;
		 }
		 else {
			 response.setData(descendingOrderList);
			 response.setErrorCode(200);
			 response.setMsg("Transaction Fetched Successfully");
			 return response;
		 }
		
	}

}
