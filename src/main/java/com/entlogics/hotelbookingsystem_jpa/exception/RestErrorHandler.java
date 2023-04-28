package com.entlogics.hotelbookingsystem_jpa.exception;

import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestErrorHandler {

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CustomErrorMessage nullPointerException(NullPointerException e) {

		CustomErrorMessage msg = new CustomErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR, "Invalid ID.Please provide valid ID to proceed further");

		return msg;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CustomErrorMessage illegalArgumentException(IllegalArgumentException e) {

		CustomErrorMessage msg = new CustomErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Object/ID passed");

		return msg;
	}

	@ExceptionHandler(TransactionRequiredException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CustomErrorMessage transactionRequiredException(TransactionRequiredException e) {

		CustomErrorMessage msg = new CustomErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR, "No Transaction Available");

		return msg;
	}

	@ExceptionHandler(PersistenceException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CustomErrorMessage persistenceException(PersistenceException e) {

		CustomErrorMessage msg = new CustomErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR, "Entity already exists in database");

		return msg;
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public CustomErrorMessage methodNotAllowedException(HttpRequestMethodNotSupportedException e) {

		CustomErrorMessage msg = new CustomErrorMessage(HttpStatus.METHOD_NOT_ALLOWED.value(),
				HttpStatus.METHOD_NOT_ALLOWED,
				"The request method is not supported for the requested resource.Please valid.");

		return msg;
	}

}
