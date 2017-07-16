package com.vodafone.exception;

public class AuthorizationException extends ServiceException{
	private final long code = 403;
	private final String message = "Authorization Exception has bean occurred";
	 
	public AuthorizationException(String message) {
	        super(message);
	 }
	 public AuthorizationException() {
	        super("Authorization Exception has occured");
	 }
	public String getMessage(){
		return message;
	}
	public long getCode(){
		return code;
	}
}