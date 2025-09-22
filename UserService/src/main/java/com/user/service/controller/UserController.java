package com.user.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.binding.LoginRequest;
import com.user.service.binding.UserBinding;
import com.user.service.entity.LoginInfo;
import com.user.service.entity.User;
import com.user.service.repo.LoginInfoRepository;
import com.user.service.service.UserService;
import com.user.service.util.APiResponse;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginInfoRepository loginInfoRepository;

	@PostMapping("/register")
	public ResponseEntity<APiResponse<User>> registerUser(@RequestBody UserBinding userBinding) {

		APiResponse<User> user = userService.addUser(userBinding);

		return new ResponseEntity<APiResponse<User>>(user, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<APiResponse<User>> loginUser(@RequestBody LoginRequest loginRequest) {

		APiResponse<User> loginUser = userService.loginUser(loginRequest);

		return new ResponseEntity<APiResponse<User>>(loginUser, HttpStatus.OK);
	}

	@GetMapping("/getInfo/{id}")
	public ResponseEntity<APiResponse<User>> getUserInfoById(@PathVariable Integer id) {

		APiResponse<User> userInfo = userService.getUserByid(id);

		return new ResponseEntity<APiResponse<User>>(userInfo, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<APiResponse<User>> updateUserInfo(@RequestBody UserBinding userBinding) {

		APiResponse<User> updatedUser = userService.updateUser(userBinding);

		return new ResponseEntity<APiResponse<User>>(updatedUser, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<APiResponse<?>> deleteUser(@PathVariable Integer id) {

		APiResponse<?> response = userService.deleteUserById(id);

		return new ResponseEntity<APiResponse<?>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/loginInfo/{userId}")
	public ResponseEntity<LoginInfo> getLastLoginInfoByUSerId(@PathVariable Integer userId){
		
	 LoginInfo loginInfo = loginInfoRepository.getSecondLatestLoginByUserId(userId);
	
	 return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
		
	}

}
