package com.vodafone.exception;

public class DatabaseCommitException extends DatabaseException {
	private final long code = 500;
	private final String message = "Internal error has bean occurred";

	public DatabaseCommitException(String message) {
        super(message);
    }
    public DatabaseCommitException()
    {
    	super("Database Commit Exception has occured");
    }
    public long getCode(){
		return code;
	}
}
