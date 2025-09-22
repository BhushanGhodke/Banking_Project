package com.account.service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.account.service.entity.AccountTransaction;

public interface AccountTransactionRepo extends JpaRepository<AccountTransaction, Integer>{

	public List<AccountTransaction> findByAccountNumber(String accountNumber);
	
}
