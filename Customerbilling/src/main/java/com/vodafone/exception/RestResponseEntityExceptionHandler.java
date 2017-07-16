package com.vodafone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value={DatabaseCommitException.class,DatabaseRollbackException.class,DatabaseException.class})
	public ResponseEntity<String> handleDataBaseException(DatabaseException ex)
	{
		ResponseEntity<String> entity = new ResponseEntity<>(ex.getMessage()+" "+ex.getCode(),HttpStatus.BAD_REQUEST);
		return entity;
	}
	@ExceptionHandler(value={UserNotFoundException.class,AuthenticationException.class,AuthorizationException.class,ServiceException.class})
	public ResponseEntity<String> handleServiceException(ServiceException ex)
	{
		ResponseEntity<String> entity = new ResponseEntity<>(ex.getMessage()+" "+ex.getCode(),HttpStatus.NOT_FOUND);
		return entity;
	}
	@ExceptionHandler(value={ org.springframework.security.core.AuthenticationException.class})
	public ResponseEntity<String>handleAuthenticationException(org.springframework.security.core.AuthenticationException ex)
	{
		ResponseEntity<String> entity = new ResponseEntity<>("Authentication Exception has occured 401",HttpStatus.NOT_FOUND);
		return entity;
	}
	@ExceptionHandler(value={AccessDeniedException.class})
	public ResponseEntity<String>handleAuthorizationException(AccessDeniedException ex)
	{
		ResponseEntity<String> entity = new ResponseEntity<>("Authorization Exception has occured 403",HttpStatus.NOT_FOUND);
		return entity;
	}
	@ExceptionHandler({AppException.class})
	public ResponseEntity<String> handleAppException(AppException ex)
	{
		ResponseEntity<String> entity = new ResponseEntity<>(ex.getMessage()+" "+ex.getCode(),HttpStatus.FORBIDDEN);
		return entity;
	}
	@ExceptionHandler({Exception.class})
	public ResponseEntity<String> handleGeneralException(Exception ex)
	{
		ResponseEntity<String> entity = new ResponseEntity<>("Unknown error has bean occurred 999",HttpStatus.FORBIDDEN);
		return entity;
	}
}