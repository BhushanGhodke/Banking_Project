package com.service.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	 List<Transaction> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);
	
	
}
