package com.vodafone.exception;

public class DatabaseRollbackException extends DatabaseException{
	private static final long code = 500;
	private final String message = "Internal error has bean occurred";

	public DatabaseRollbackException() {
        super("Database Rollback Exception has occured");
    }
	public DatabaseRollbackException(String message) {
    	super(message);
    }
    public String getMessage(){
		return message;
    }
    public long getCode(){
		return code;
    }
}
