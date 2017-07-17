package com.vodafone.dto;

import lombok.Data;

@Data
public class Response {

	private String message;
	private long code;
	public Response(String message, long code) {
		super();
		this.message = message;
		this.code = code;
	}
	
	
	
}
