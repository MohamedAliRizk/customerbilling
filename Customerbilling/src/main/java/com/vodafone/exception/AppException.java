package com.vodafone.exception;

public abstract class AppException extends Exception {
	private final static long code = 999;
	private final String message = "Unknown error has bean occurred";
	
	public AppException() {
		super("Unknown error has bean occurred");
	}

	public AppException(String message) {
		super(message);
	}
	public String getMessage(){
		return message;
	}
	public long getCode(){
		return code;
	}
}
