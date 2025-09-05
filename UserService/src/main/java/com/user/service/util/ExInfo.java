package com.user.service.util;

import java.time.LocalDate;

public class ExInfo {

	private String msg;
	private Integer errorCode;
	private LocalDate time;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public LocalDate getTime() {
		return time;
	}
	public void setTime(LocalDate time) {
		this.time = time;
	}
	
	
}
