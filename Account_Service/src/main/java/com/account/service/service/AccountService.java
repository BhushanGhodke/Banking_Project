package com.account.service.service;

import javax.security.auth.login.AccountNotFoundException;

import com.account.service.binding.AccountBinding;
import com.account.service.binding.TransactionRequest;
import com.account.service.entity.Account;
import com.account.service.entity.User;
import com.account.service.util.APiResponse;

public interface AccountService {
	
	public APiResponse<Account> createAccount(AccountBinding accountBinding);
	
	public APiResponse<User> getUserInfoById(Integer id);
	
	public APiResponse<Account> getAccountInfoByUserId(Integer userId);
	
	public APiResponse<Boolean> getAccountOpenedOrNotByUserId(Integer userId);
	
	public APiResponse<Double> getBalanceByAccountNumber(String accountNumber) throws AccountNotFoundException;
	
	public APiResponse<Boolean> credit(TransactionRequest txRequest) throws AccountNotFoundException;
	
	public APiResponse<Boolean> debit( TransactionRequest txRequest) throws AccountNotFoundException;
}
