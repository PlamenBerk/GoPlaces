package com.goplaces.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserException.class)
	public final ResponseEntity<Advice> handleUserNotFoundException(UserException ex) {

		Advice apiException = new Advice(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getClass().getSimpleName(), ex);

		return new ResponseEntity<Advice>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Advice> unexpectedExceptions(Exception ex) {

		Advice apiException = new Advice(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getClass().getSimpleName(), ex);

		return new ResponseEntity<Advice>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
