package com.account.service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.account.service.entity.Account;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Integer>{

	public Optional<Account> findByUserId(Integer userId);
	
	public Optional<Account> findByAccountNumber(String accountNumber);
}
