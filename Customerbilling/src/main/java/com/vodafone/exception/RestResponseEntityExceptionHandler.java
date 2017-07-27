package com.vodafone.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		// TODO Auto-generated method stub
		//return super.handleBindException(ex, headers, status, request);
		List<FieldError> errors = ex.getFieldErrors();
		List<Message> message = new ArrayList<>();
		for (FieldError fieldError : errors) {
			Message m = new Message();
			m.setData(fieldError.getDefaultMessage());
			message.add(m);
		}
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

	}
	
@Override
protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
	// TODO Auto-generated method stub
	//return super.handleMethodArgumentNotValid(ex, headers, status, request);
	List<FieldError> errors = ex.getBindingResult().getFieldErrors();
	List<Message> message = new ArrayList<>();
	for (FieldError fieldError : errors) {
		Message m = new Message();
		m.setData(fieldError.getDefaultMessage());
		message.add(m);
	}
	return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
}
@Override
protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
	// TODO Auto-generated method stub
	
	Response res = new Response("Please Enter Valid Data", 400);
	return  new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);

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