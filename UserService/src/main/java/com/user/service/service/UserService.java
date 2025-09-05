package com.user.service.service;

import com.user.service.binding.LoginRequest;
import com.user.service.binding.UserBinding;
import com.user.service.entity.User;
import com.user.service.util.APiResponse;

public interface UserService {

 public APiResponse<User> addUser(UserBinding userBinding);
 
 public APiResponse<User> loginUser(LoginRequest login);
 
 public APiResponse<User> getUserByid(Integer id);
 
 public APiResponse<User> updateUser(UserBinding userBinding);
 
 public APiResponse<?> deleteUserById(Integer id);
	
}
