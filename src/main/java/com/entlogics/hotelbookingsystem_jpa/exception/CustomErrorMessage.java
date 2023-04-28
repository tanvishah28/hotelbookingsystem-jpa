package com.entlogics.hotelbookingsystem_jpa.exception;

import org.springframework.http.HttpStatus;

public class CustomErrorMessage {

	private int statusCode;
	private HttpStatus httpStatus;
	private String errorMsg;
	
	public CustomErrorMessage(int statusCode, HttpStatus httpStatus, String errorMsg) {
		super();
		this.statusCode = statusCode;
		this.httpStatus = httpStatus;
		this.errorMsg = errorMsg;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "CustomErrorMessage [statusCode=" + statusCode + ", httpStatus=" + httpStatus + ", errorMsg=" + errorMsg
				+ "]";
	}
}
