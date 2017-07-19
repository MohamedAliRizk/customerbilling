package com.vodafone.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vodafone.dto.AccountCredentials;
import com.vodafone.dto.Response;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final Logger LOGGER = Logger.getLogger(JWTLoginFilter.class);

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
		LOGGER.info("Attempt to autenticate customer with username : " + creds.getUsername());
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
				creds.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		LOGGER.info("Successfully authenticate Customer with username : " + auth.getName() + " with details : " + auth.getDetails());
		TokenAuthenticationService.addAuthentication(res, auth.getName());
	}
	
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		LOGGER.error("An Error Occured while adding authentication token in Redis Cache");
		SecurityContextHolder.clearContext();
		
		/*Response resMsg = new Response("Authorization Exception has occured", HttpStatus.UNAUTHORIZED.value());
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		response.getWriter().println(ow.writeValueAsString(resMsg));
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON.toString());
	    response.getWriter().flush();*/
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Exception has occured");
	}
}
