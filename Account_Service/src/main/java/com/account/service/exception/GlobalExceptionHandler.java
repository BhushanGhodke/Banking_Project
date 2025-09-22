package com.account.service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.account.service.util.ExInfo;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(value = AccountNotFoundException.class)
     public ResponseEntity<ExInfo> accountNotFoundException(AccountNotFoundException e) {
    	 
		ExInfo ex = new ExInfo();
		ex.setErrorCode(500);
		ex.setMsg(e.getMessage());
		ex.setTime(LocalDateTime.now());
		return new ResponseEntity<ExInfo>(ex, HttpStatus.BAD_REQUEST);
		
     }
	
}
