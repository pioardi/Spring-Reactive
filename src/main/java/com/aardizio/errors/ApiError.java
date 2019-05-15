package com.aardizio.errors;

import org.springframework.http.HttpStatus;

public class ApiError {
	
	private HttpStatus status;
	private String message;
	private String errorCode;
	private String level;
	
	public ApiError(HttpStatus status, String errorCode , String level  , String message) {
		super();
		this.status = status;
		this.message = message;
		this.errorCode = errorCode;
		this.level = level;
	}
	
	public ApiError(HttpStatus status, String errorCode , String level) {
		super();
		this.status = status;
		this.errorCode = errorCode;
		this.level = level;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
}