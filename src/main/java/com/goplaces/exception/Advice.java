package com.goplaces.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Advice {

	private HttpStatus status;
	private int statusCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String system;
	private String message;

	private Advice() {
		timestamp = LocalDateTime.now();
	}

	public Advice(HttpStatus status) {
		this();
		this.status = status;
	}

	public Advice(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.system = "Unexpected error";
		this.message = ex.getLocalizedMessage();
	}

	public Advice(HttpStatus status, int statusCode, String message, Throwable ex) {
		this();
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
		this.system = ex.getClass().getSimpleName();

	}

	public HttpStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getSystem() {
		return system;
	}

	public String getMessage() {
		return message;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
