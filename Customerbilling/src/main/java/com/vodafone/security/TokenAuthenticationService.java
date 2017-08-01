package com.vodafone.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vodafone.exception.AuthenticationException;
import com.vodafone.exception.ServiceException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenAuthenticationService {

	private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

	static final long EXPIRATIONTIME = 3600000; // 1 hour
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static String addAuthentication(HttpServletResponse res, String username) throws ServiceException {
		logger.info("Generateing JWT for user :{} ", username);

		String JWT = null;

		try {

			String roleNewValue="USER";
			if(username.equals("bill")){
				roleNewValue="ROOT";
			}
			
			JWT = Jwts.builder().setSubject(username).claim("roles",roleNewValue ).setIssuedAt(generateCurrentDate())
					.setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, SECRET).compact();

		} catch (Exception ex) {

			logger.error("{} occured while building JWT for user : {}, , full stack trace is: [{}]",ex.getClass().getSimpleName(), username, ex);

			throw new AuthenticationException("{} occured while building JWT " + ex.getMessage());
		}

		// LOGGER.info("Saving Token for user : " + username + " in Redis");
		// RedisUtil.INSTANCE.set(username, JWT);

		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		return JWT;
	}

	static String addAuthentication(Claims claims) throws ServiceException {

		logger.info("Generateing Token for claims : {}", claims);

		String JWT;
		try {
			
			JWT = Jwts.builder().setClaims(claims).setIssuedAt(generateCurrentDate())
					.setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, SECRET).compact();

		} catch (Exception ex) {
			logger.error("{} occured while building JWT from claims {}, full stack trace is: [{}]",ex.getClass().getSimpleName(), claims, ex);

			throw new AuthenticationException(ex.getClass().getSimpleName()+" occured while building JWT from claims");
		}

		// LOGGER.info("Saving Token for user : " + claims.getSubject() + " in
		// Redis");
		// RedisUtil.INSTANCE.set(claims.getSubject(), JWT);

		return JWT;
	}

	static Authentication getAuthentication(HttpServletRequest request) throws ServiceException {
		
		String token = request.getHeader(HEADER_STRING);
		
		logger.info("An attempt to validate JWT : {}" , token);

		if (token != null) {

			token = token.replace(TOKEN_PREFIX, "").trim();

			String user = null;
			List<GrantedAuthority> grantedAuthorities;
			try {
				
				
				user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();

				logger.info("User {} has been extracted from JWT");
				
				String allRoles = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token)
						.getBody().get("roles").toString();
				
				logger.info("User {} has the following roles [{}]",allRoles);

				
				List<String> roleString = Arrays.asList(allRoles.split(","));

				grantedAuthorities = new ArrayList<GrantedAuthority>();
				
				Iterator<String> it = roleString.iterator();
				while (it.hasNext()) {
					String currentRole= it.next();
					grantedAuthorities.add(new SimpleGrantedAuthority(currentRole));
					logger.debug("User {} role {} added to GrantedAuthorities",user,currentRole);
				}

			} catch (MalformedJwtException | UnsupportedJwtException | SignatureException | ExpiredJwtException
					| IllegalArgumentException ex) {

				logger.error("{} occured while parsing JWT, full stack trace: [{}]",ex.getClass().getSimpleName(),ex);
				
				SecurityContextHolder.clearContext();

				throw new AuthenticationException(ex.getClass().getSimpleName()+" occured while building JWT from claims");
			} catch (Exception ex) {


				logger.error("{} occured while parsing JWT, full stack trace: [{}]",ex.getClass().getSimpleName(),ex);
				
				SecurityContextHolder.clearContext();

				throw new ServiceException(ex.getClass().getSimpleName()+" occured while building JWT from claims");
			}

			logger.debug("Token : " + token + " relate to user : " + user);

			// Another way save active users in set
			// String cachedToken = RedisUtil.INSTANCE.get(user);

			// LOGGER.info("Found token : " + cachedToken + " in Redis for user
			// : " + user);
			// boolean isTokenAlreadyExist = token.equals(cachedToken);
			// LOGGER.info("Token : " + token + " relate to user : " + user
			// + (isTokenAlreadyExist ? " is already" : " is not") + " exist in
			// redis");

			return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
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