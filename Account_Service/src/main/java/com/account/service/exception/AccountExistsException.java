package com.account.service.exception;

public class AccountExistsException extends RuntimeException {

	public AccountExistsException(String msg) {
	
		super(msg);
	}
}
