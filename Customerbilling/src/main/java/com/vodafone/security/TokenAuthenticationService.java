package com.vodafone.security;

import static java.util.Collections.emptyList;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vodafone.exception.ServiceException;
import com.vodafone.util.RedisUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	private static final Logger LOGGER = Logger.getLogger(TokenAuthenticationService.class);

	static final long EXPIRATIONTIME = 3600000; // 1 hour
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static String addAuthentication(HttpServletResponse res, String username) {
		LOGGER.info("Generateing Token for user : " + username);
		
		String JWT = null;
		
		try{
			
			Claims claims = Jwts.claims().setSubject(username);
			
			//TODO add real roles based on API call or DB cached user roles 
			claims.put("roles","ADMIN,ROOT");
		
			JWT = Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(generateCurrentDate())
					.setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, SECRET).compact();

		}catch(Exception ex){
			
			//logging sl4j
			
//			throw new ServiceException("Error while building JWT token "+ex.getMessage());
		}
		 
		LOGGER.info("Saving Token for user : " + username + " in Redis");
		// RedisUtil.INSTANCE.set(username, JWT);

		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		return JWT;
	}

	static String addAuthentication(Claims claims) {
		LOGGER.info("Generateing Token for claims : " + claims);
		String JWT = Jwts.builder().setClaims(claims).setIssuedAt(generateCurrentDate())
				.setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, SECRET).compact();

		LOGGER.info("Saving Token for user : " + claims.getSubject() + " in Redis");
//		RedisUtil.INSTANCE.set(claims.getSubject(), JWT);

		return JWT;
	}

	static Authentication getAuthentication(HttpServletRequest request) throws ServiceException {
		String token = request.getHeader(HEADER_STRING);
		LOGGER.info("Validating Token : " + token);

		if (token != null) {

			token = token.replace(TOKEN_PREFIX, "").trim();

			String user = null;
			try {
				// parse the token.
				user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();

			} catch (AuthenticationException ex) {

				SecurityContextHolder.clearContext();

				// Logging detailed ex
				throw new com.vodafone.exception.AuthenticationException("Customer is not authenticated");
			} catch (Exception ex) {

				// Logging detailed ex
				throw new ServiceException("");
			}

			LOGGER.debug("Token : " + token + " relate to user : " + user);

			// Another way save active users in set
			String cachedToken = RedisUtil.INSTANCE.get(user);
			LOGGER.info("Found token : " + cachedToken + " in Redis for user : " + user);
			boolean isTokenAlreadyExist = token.equals(cachedToken);
			LOGGER.info("Token : " + token + " relate to user : " + user
					+ (isTokenAlreadyExist ? " is already" : " is not") + " exist in redis");

			return isTokenAlreadyExist ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
		}
		return null;
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.setIssuedAt(generateCurrentDate());
			refreshedToken = addAuthentication(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private static Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	private static Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRATIONTIME);
	}
}
