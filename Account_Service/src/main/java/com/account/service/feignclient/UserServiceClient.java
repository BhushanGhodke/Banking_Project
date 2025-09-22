package com.account.service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.account.service.entity.User;
import com.account.service.util.APiResponse;

@FeignClient(name = "user-service")
public interface UserServiceClient {

	@GetMapping("/user/getInfo/{id}")
	public APiResponse<User> getUserInfoById(@PathVariable Integer id);
	
}
