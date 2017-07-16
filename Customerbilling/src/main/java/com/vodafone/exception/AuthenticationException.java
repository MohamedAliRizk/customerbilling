package com.vodafone.exception;

public class AuthenticationException extends ServiceException{
	private final long code = 401;
	private final String message = "Authentication Exception has bean occurred";
	public AuthenticationException(String message) {
	        super(message);
	 }
	 public AuthenticationException() {
	        super("Authentication Exception has occured");
	 }
	public String getMessage(){
		return message;
	}
	public long getCode(){
		return code;
	}
}
