package com.blog.application.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		Authorization
		String requestHeader = request.getHeader("Authorization");
		log.info("Request Header : {}",requestHeader);
		
		String userName=null;
		String token=null;
	
		if (requestHeader!=null && requestHeader.startsWith("Bearer") ) {
			token=requestHeader.substring(7);
			try {
				
			userName = this.jwtHelper.getUsernameFromToken(token);
				
			} catch (IllegalArgumentException e) {
				log.info("Illegal Argument While Fetching the username!!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				log.info("Given Jwt Token is Expired!!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				log.info("some changed has done in token!! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			log.info("Invalid Header Value");
		}
		
		if (userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
		//	fetch user detail from usrename
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
					

			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

			if (validateToken) {
				
//			set the Authentication
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				log.info("Validations Fails!!");
			}
			
		}
	
		
		filterChain.doFilter(request,response);
		
		
	}
	

}
