package com.service.transaction.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.service.transaction.util.ExInfo;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler( value = TransferFundException.class)
	public ResponseEntity<ExInfo> transferFundEx(TransferFundException e){
		
		ExInfo ex= new ExInfo();
		ex.setErrorCode(500);
		ex.setMsg(e.getMessage());
		ex.setTime(LocalDateTime.now());
		return new ResponseEntity<ExInfo>(ex,HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler( value = AccountNotFoundException.class)
	public ResponseEntity<ExInfo> AccountNotFoundEx(AccountNotFoundException e){
		
		ExInfo ex= new ExInfo();
		ex.setErrorCode(500);
		ex.setMsg(e.getMessage());
		ex.setTime(LocalDateTime.now());
		return new ResponseEntity<ExInfo>(ex,HttpStatus.BAD_REQUEST);
		
	}
}
