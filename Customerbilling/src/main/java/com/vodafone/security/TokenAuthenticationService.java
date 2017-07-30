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

import com.vodafone.exception.ServiceException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenAuthenticationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationService.class);
	static final long EXPIRATIONTIME = 3600000; // 1 hour
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static String addAuthentication(HttpServletResponse res, String username) {
		LOGGER.info("Generateing Token for user : " + username);

		String JWT = null;

		try {

			JWT = Jwts.builder().setSubject(username).claim("roles", "ROOT,ADMIN").setIssuedAt(generateCurrentDate())
					.setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, SECRET).compact();

		} catch (Exception ex) {

			// TODO appropirate error logging with ex details

			// throw new ServiceException("Error while building JWT token
			// "+ex.getMessage());
		}

		// LOGGER.info("Saving Token for user : " + username + " in Redis");
		// RedisUtil.INSTANCE.set(username, JWT);

		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		return JWT;
	}

	static String addAuthentication(Claims claims) {

		LOGGER.info("Generateing Token for claims : " + claims);

		String JWT = Jwts.builder().setClaims(claims).setIssuedAt(generateCurrentDate())
				.setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, SECRET).compact();

		// LOGGER.info("Saving Token for user : " + claims.getSubject() + " in
		// Redis");
		// RedisUtil.INSTANCE.set(claims.getSubject(), JWT);

		return JWT;
	}

	static Authentication getAuthentication(HttpServletRequest request) throws ServiceException {
		String token = request.getHeader(HEADER_STRING);
		LOGGER.info("Validating Token : " + token);

		if (token != null) {

			token = token.replace(TOKEN_PREFIX, "").trim();

			String user = null;
			List<GrantedAuthority> roles;
			try {
				// parse the token
				user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();

				List<String> roleString = Arrays.asList(Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token)
						.getBody().get("roles").toString().split(","));

				roles = new ArrayList<GrantedAuthority>();

				Iterator<String> it = roleString.iterator();
				while (it.hasNext()) {
					roles.add(new SimpleGrantedAuthority(it.next()));
				}

			} catch (MalformedJwtException | UnsupportedJwtException | SignatureException | ExpiredJwtException
					| IllegalArgumentException ex) {

				SecurityContextHolder.clearContext();

				// TODO Logging detailed ex
				throw new com.vodafone.exception.AuthenticationException("Customer is not authenticated");
			} catch (Exception ex) {

				// TODO Logging detailed ex
				throw new ServiceException("");
			}

			LOGGER.debug("Token : " + token + " relate to user : " + user);

			// Another way save active users in set
			// String cachedToken = RedisUtil.INSTANCE.get(user);

			// LOGGER.info("Found token : " + cachedToken + " in Redis for user
			// : " + user);
			// boolean isTokenAlreadyExist = token.equals(cachedToken);
			// LOGGER.info("Token : " + token + " relate to user : " + user
			// + (isTokenAlreadyExist ? " is already" : " is not") + " exist in
			// redis");

			return new UsernamePasswordAuthenticationToken(user, null, roles);
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