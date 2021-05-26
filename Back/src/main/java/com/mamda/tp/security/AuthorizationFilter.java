package com.mamda.tp.security;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mamda.tp.model.TPUser;
import com.mamda.tp.SpringApplicationContext;
import com.mamda.tp.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class AuthorizationFilter extends BasicAuthenticationFilter {

	private JWTProvider jwtProvider;
	private final Log logger = LogFactory.getLog(AuthorizationFilter.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	public AuthorizationFilter(AuthenticationManager authManager,JWTProvider jwtProvider) {
		super(authManager);
		this.jwtProvider = jwtProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(SecurityCanstants.HEADER_STRING);

		if (header == null || !header.startsWith(SecurityCanstants.TOKEN_PREFIX)) {
			SecurityContextHolder.clearContext();
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		String token = request.getHeader(SecurityCanstants.HEADER_STRING);
		if (token != null) {
			try{
//				boolean isTokenValid = this.jwtProvider.validateJwtToken(token);
//				if(!isTokenValid)
//					return null;
				Integer id = jwtProvider.getIdFromJWTToken(token);
				if (id != null) {
					UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImp");
					TPUser user = userService.getUser(id);
					UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

					return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
				}
			}catch(ExpiredJwtException ex){
				this.logger.error("Your Token is Expired");
			}
		}
		return null;
	}

}
