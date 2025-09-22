package com.service.transaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.service.transaction.binding.TransactionRequest;
import com.service.transaction.util.APiResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "account-service")
public interface AccountClient {

	@GetMapping("/account/getBalance/{accountNumber}")
	public ResponseEntity<APiResponse<Double>> getBalance(@PathVariable String accountNumber);

	@PostMapping("/account/credit")
	public ResponseEntity<APiResponse<Boolean>> credit(@RequestBody TransactionRequest txRequest );

	@PostMapping("/account/debit")
	public ResponseEntity<APiResponse<Boolean>> debit(@RequestBody TransactionRequest txRequest );
	
	

}
