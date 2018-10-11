package com.goplaces.exception;

import java.io.IOException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
	public final ResponseEntity<Advice> handleUserNotFoundException(Exception ex) {

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

	@ExceptionHandler(IOException.class)
	public final ResponseEntity<Advice> ioException(Exception ex) {

		Advice apiException = new Advice(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getClass().getSimpleName(), ex);

		return new ResponseEntity<Advice>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Advice> constraintViolationException(ConstraintViolationException ex) {
		StringBuilder message = new StringBuilder();
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		for (ConstraintViolation<?> violation : violations) {
			message.append(violation.getMessage().concat(";"));
		}
		System.err.println(message.toString());
		Advice apiException = new Advice(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				message.toString(), ex);

		return new ResponseEntity<Advice>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
