package com.vodafone.commonExceptions;

public class InternalServerError extends GenericException{

	public InternalServerError(String message, String id, String rootCause) {
		super(message, id, rootCause);
		// TODO Auto-generated constructor stub
	}
	
	public InternalServerError(String message, String rootCause) {
		super(message, rootCause);
		// TODO Auto-generated constructor stub
	}
}