package com.service.transaction.service;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import com.service.transaction.binding.TransferFund;
import com.service.transaction.entity.Transaction;

public interface TransactionService {

	public Transaction transferAmount(TransferFund transferFund) throws AccountNotFoundException;
	
	List<Transaction> getTransaction(String accountNumber);
}
