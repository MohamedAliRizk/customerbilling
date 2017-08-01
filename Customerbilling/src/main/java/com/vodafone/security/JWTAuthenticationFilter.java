package com.vodafone.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.vodafone.exception.ServiceException;

@Component
public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		Authentication authentication = null;
		try {

			authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);

		} catch (ServiceException e) {

			HttpServletResponse httpServlet = (HttpServletResponse) response;

			httpServlet.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			PrintWriter writer = httpServlet.getWriter();
			writer.println("HTTP Status 401 :: " + e.getMessage());

			httpServlet.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Exception has occured");

		}
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
//		return;
	}
}
