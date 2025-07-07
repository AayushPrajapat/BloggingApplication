package com.blog.application.controllers;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.application.dtos.UserDto;
import com.blog.application.entities.JwtRequest;
import com.blog.application.entities.JwtResponse;
import com.blog.application.security.JwtHelper;
import com.blog.application.services.UserService;


@RestController
@RequestMapping("/auth")
public class AuthController {

	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping
	public ResponseEntity<UserDto> getCurrentUser(Principal principal){
		String name = principal.getName();
		return new ResponseEntity<UserDto>(mapper.map(userDetailsService.loadUserByUsername(name),UserDto.class),HttpStatus.OK);
	}
	
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
		
		this.doAuthenticate(jwtRequest.getEmail(),jwtRequest.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
		
		String generateToken = this.jwtHelper.generateToken(userDetails);
		
		UserDto userDto = mapper.map(userDetails,UserDto.class);
		
		JwtResponse jwtResponse = JwtResponse.builder()
		.jwtToken(generateToken)
		.userName(jwtRequest.getEmail()).build();
		
		return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);	
	}

	private void doAuthenticate(String email, String password) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		try {
			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Username or Password!!");
		}
		
	}
	
	

}

