package com.account.service.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.service.binding.KycBinding;
import com.account.service.entity.KycDetails;
import com.account.service.exception.KycUpdateException;
import com.account.service.repo.KycDetailsRepo;
import com.account.service.util.APiResponse;

@Service
public class KycDetailsServiceImpl implements KycDetailsService {

	@Autowired
	private KycDetailsRepo kycDetailsRepo;

	@Override
	public APiResponse<KycDetails> getKycDetailsByAccountId(Integer accountId) {

		APiResponse<KycDetails> response = new APiResponse<>();

		Optional<KycDetails> kycStatus = kycDetailsRepo.getKycDetailsByAccountId(accountId);

		if (kycStatus.isPresent()) {
			response.setData(kycStatus.get());
			response.setErrorCode(200);
			response.setMsg("KYC STATUS FETCHED SUCCESSFULLY");
			return response;
		} else {

		
			response.setErrorCode(500);
			response.setMsg("KYC STATUS NOT FOUND");
			return response;
		}
	}

	@Override
	public APiResponse<KycDetails> startKycUpdationProcess(KycBinding kycBinding) {

		APiResponse<KycDetails> response = new APiResponse<>();
		
		Optional<KycDetails> kycDetails= kycDetailsRepo.getKycDetailsByUserId(kycBinding.getUserId());
	
		if(kycDetails.isPresent()) {
			
			BeanUtils.copyProperties(kycBinding, kycDetails.get());
			kycDetails.get().setStatus("UPDATED");
			kycDetails.get().setAccountId(0);
			KycDetails save = kycDetailsRepo.save(kycDetails.get());
			response.setData(save);
			response.setErrorCode(200);
			response.setMsg("KYC UPDATED");
			return response;
		}
		
		KycDetails kyc = new KycDetails();

		BeanUtils.copyProperties(kycBinding, kyc);
		kyc.setStatus("UPDATED");
		kyc.setAccountId(0);
		KycDetails save = kycDetailsRepo.save(kyc);

		if (save.getKycId() != 0) {

			response.setData(save);
			response.setErrorCode(200);
			response.setMsg("KYC UPDATED");
			return response;
		} else {

			response.setData(null);
			response.setErrorCode(500);
			response.setMsg(new KycUpdateException("Kyc Updation Error").getMessage());
			return response;
		}

	}
	
	
	@Override
	public APiResponse<KycDetails> getKycDetailsByUserId(Integer userId) {

		APiResponse<KycDetails> response = new APiResponse<>();

		Optional<KycDetails> kycStatus = kycDetailsRepo.getKycDetailsByUserId(userId);

		if (kycStatus.isPresent()) {
			response.setData(kycStatus.get());
			response.setErrorCode(200);
			response.setMsg("KYC STATUS FETCHED SUCCESSFULLY");
			return response;
		} else {

			response.setData(null);
			response.setErrorCode(500);
			response.setMsg("KYC STATUS NOT FOUND");
			return response;
		}
	}
}
