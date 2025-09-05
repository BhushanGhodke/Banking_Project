package com.user.service.exception;

public class UserNotFoundException  extends RuntimeException{

	public UserNotFoundException(String msg) {
	
		super(msg);
	}
}
