package com.vodafone.exception;

public class DatabaseException extends AppException{
	private final long code = 500;
	private final String message = "Internal error has bean occurred";

	public DatabaseException() {
		super("Unknown Database error has bean occurred");
    }
    public DatabaseException(String message) {
        super(message);
    }
    public long getCode(){
		return code;
	}
}