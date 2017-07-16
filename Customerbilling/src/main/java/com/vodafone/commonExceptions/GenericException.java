package com.vodafone.commonExceptions;

public abstract class GenericException extends RuntimeException {

	private String message;
	private String id;
	private String rootCause;

	public GenericException(String message) {
		this.message = message;
	}

	public GenericException(String message, String id) {

		this.message = message;
		this.id = id;

	}

	public GenericException(String message, String id, String rootCause) {

		this.message = message;
		this.id = id;
		this.rootCause = rootCause;
	}

	public String getMessage() {
		return message;
	}

	public String getId() {
		return id;
	}

	public String getRootCause() {
		return rootCause;
	}
	
	
}
