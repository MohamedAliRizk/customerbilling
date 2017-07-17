package com.vodafone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.vodafone.dto.Response;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = { DatabaseCommitException.class, DatabaseRollbackException.class,
			DatabaseException.class })
	public ResponseEntity<?> handleDataBaseException(DatabaseException ex) {
		Response res = new Response(ex.getMessage(), ex.getCode());
		ResponseEntity<Response> entity = new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		return entity;
	}

	@ExceptionHandler(value = { UserNotFoundException.class, AuthenticationException.class,
			AuthorizationException.class, ServiceException.class })
	public ResponseEntity<?> handleServiceException(ServiceException ex) {
		Response res = new Response(ex.getMessage(), ex.getCode());
		ResponseEntity<Response> entity = new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		return entity;
	}

	@ExceptionHandler(value = { org.springframework.security.core.AuthenticationException.class })
	public ResponseEntity<?> handleAuthenticationException(
			org.springframework.security.core.AuthenticationException ex) {
		Response res = new Response("Authentication Exception has occured", 401);
		ResponseEntity<Response> entity = new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		return entity;
	}

	@ExceptionHandler(value = { AccessDeniedException.class })
	public ResponseEntity<?> handleAuthorizationException(AccessDeniedException ex) {
		Response res = new Response("Authorization Exception has occured", 403);
		ResponseEntity<Response> entity = new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		return entity;
	}

	@ExceptionHandler({ AppException.class })
	public ResponseEntity<?> handleAppException(AppException ex) {
		Response res = new Response(ex.getMessage(), ex.getCode());
		ResponseEntity<Response> entity = new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
		return entity;
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<?> handleGeneralException(Exception ex) {
		Response res = new Response("Unknown error has bean occurred", 999);
		ResponseEntity<Response> entity = new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
		return entity;
	}
}