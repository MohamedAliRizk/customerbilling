//package com.vodafone.security;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtAuthenticationProvider implements AuthenticationManager {
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//		// TODO SOAP client call to authenticate the user on the backend
//
//		// Collection<GrantedAuthority> roles = new ArrayList<>();
//		// roles.add(new SimpleGrantedAuthority("ADMIN"));
//		// roles.add(new SimpleGrantedAuthority("ROOT"));
//
//		return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),
//				authentication.getAuthorities());
//	}
//
//}
