package com.account.service.service;

import java.util.List;

import com.account.service.entity.AccountTransaction;
import com.account.service.util.APiResponse;

public interface TransactionService {

	public APiResponse<List<AccountTransaction>> getTransactionDetailsByAccountNumber(String accountNumber);
	 
}
