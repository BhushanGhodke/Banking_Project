package com.user.service.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.user.service.util.ExInfo;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(value=UserAlredyExists.class)
	public ResponseEntity<ExInfo> userAlreadyExistException(UserAlredyExists e){
		
		ExInfo exinfo= new ExInfo();
		exinfo.setErrorCode(500);
		exinfo.setMsg("User Already Exists");
		exinfo.setTime(LocalDate.now());
		return new ResponseEntity<>(exinfo, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ExInfo> userNotFoundExceptionHandler(UserNotFoundException e){
		
		ExInfo exInfo= new ExInfo();
		exInfo.setErrorCode(500);
		exInfo.setMsg(e.getMessage());
		exInfo.setTime(LocalDate.now());
		return new ResponseEntity<ExInfo>(exInfo, HttpStatus.BAD_REQUEST);
	}
}
