package com.user.service.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.service.binding.LoginRequest;
import com.user.service.binding.UserBinding;
import com.user.service.entity.User;
import com.user.service.exception.UserAlredyExists;
import com.user.service.exception.UserNotFoundException;
import com.user.service.repo.UserRepository;
import com.user.service.util.APiResponse;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public APiResponse<User> addUser(UserBinding userBinding) {

		APiResponse<User> response = new APiResponse<>();
		Optional<User> userDetail = userRepository.findByUsername(userBinding.getUsername());

		if (userDetail.isEmpty()) {

			User user = new User();
			BeanUtils.copyProperties(userBinding, user);
			User save = userRepository.save(user);
			response.setData(save);
			response.setErrorCode(200);
			response.setMsg("User Registerd Successfully");
			return response;

		} else {
			throw new UserAlredyExists("User Already Exists");
		}

	}

	@Override
	public APiResponse<User> loginUser(LoginRequest login) {
		APiResponse<User> response = new APiResponse<>();
		Optional<User> userDetails = userRepository.findByUsername(login.getUsername());

		if (userDetails.isPresent()) {

			if (userDetails.get().getPassword().equals(login.getPassword())) {

				response.setData(userDetails.get());
				response.setMsg("Login Success");
				response.setErrorCode(200);
				return response;
			} else {

				response.setErrorCode(500);
				response.setData(null);
				response.setMsg("Invalid Username or Password");
				return response;
			}

		} else {

			throw new UserNotFoundException("User Not Found");
		}

	}

	@Override
	public APiResponse<User> getUserByid(Integer id) {

		APiResponse<User> response = new APiResponse<>();
		Optional<User> userById = userRepository.findById(id);

		if (userById.isPresent()) {
			response.setData(userById.get());
			response.setErrorCode(200);
			response.setMsg("Success");
			return response;
		} else {
			throw new UserNotFoundException("User Not Found with id ::" + id);
		}

	}

	@Override
	public APiResponse<User> updateUser(UserBinding userBinding) {

		APiResponse<User> response = new APiResponse<>();
		Optional<User> userInfo = userRepository.findByUsername(userBinding.getUsername());

		if (userInfo.isPresent()) {

			User user = new User();
			BeanUtils.copyProperties(userInfo.get(), user);
			User save = userRepository.save(user);
			response.setData(save);
			response.setErrorCode(200);
			response.setMsg("User Updated Successfully");
			return response;
		}
		throw new UserNotFoundException("User Not Found ");

	}

	@Override
	public APiResponse<?> deleteUserById(Integer id) {

		APiResponse<User> response = new APiResponse<>();

		Optional<User> userInfo = userRepository.findById(id);

		if (userInfo.isPresent()) {

			userRepository.delete(userInfo.get());
			response.setData(null);
			response.setErrorCode(200);
			response.setMsg("User Deleted Successfully");
			return response;

		}

		throw new UserNotFoundException("User Not Found");
	}
}
