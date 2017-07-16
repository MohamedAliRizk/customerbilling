package com.vodafone.commonExceptions;

public class CustomerNotFoundException extends GenericException{

	public CustomerNotFoundException(String message, String id) {
		super(message, id);
	}
}