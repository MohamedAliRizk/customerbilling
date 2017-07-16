package com.vodafone.exception;

public class UserNotFoundException extends ServiceException{
	private final long code = 417;
	private final String message = "User not found Exception has occurred";
	 
	public UserNotFoundException() {
	        super("User not found Exception has occured");
	 }
	public UserNotFoundException(String message) {
		 super(message);
	 }
	public String getMessage(){
			return message;
	}
	public long getCode(){
			return code;
	 }
}