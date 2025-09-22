package com.account.service.service;

import com.account.service.binding.KycBinding;
import com.account.service.entity.KycDetails;
import com.account.service.util.APiResponse;

public interface KycDetailsService {

	public APiResponse<KycDetails> getKycDetailsByAccountId(Integer accountId);
	
	public APiResponse<KycDetails> startKycUpdationProcess(KycBinding kycBinding);
	
	public APiResponse<KycDetails> getKycDetailsByUserId(Integer userId);
	
	
}
