package com.vodafone.exception;

public class ServiceException extends AppException {
	private final long code = 520;
	private final String message = "Internal error has bean occurred";

	public ServiceException() {
		super("unknown Service error has occurred");
	}
	public ServiceException(String msg) {
		super(msg);
	}
	public String getMessage(){
		return message;
	}
	public long getCode(){
		return code;
	}
}
