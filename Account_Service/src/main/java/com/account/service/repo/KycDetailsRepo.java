package com.account.service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.account.service.entity.KycDetails;

public interface KycDetailsRepo extends JpaRepository<KycDetails, Integer>{

	public Optional<KycDetails> getKycDetailsByUserId(Integer userId);
	
	public Optional<KycDetails> getKycDetailsByAccountId(Integer accountId);
}
