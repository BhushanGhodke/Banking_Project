package com.user.service.exception;

public class UserAlredyExists extends RuntimeException {

	public UserAlredyExists(String msg) {

		super(msg);
	
	}
}
