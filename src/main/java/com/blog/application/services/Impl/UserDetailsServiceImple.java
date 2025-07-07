package com.blog.application.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.blog.application.entities.User;
import com.blog.application.repositories.UserRepository;

@Service
public class UserDetailsServiceImple implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	User user = this.userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found of Given email..."));
		
		return user;
	}
	

}
