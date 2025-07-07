package com.blog.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.blog.application.security.JwtAuthenticationFilter;

@Configuration
@EnableWebMvc
@EnableWebSecurity	
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	/*
	public final String[] PUBLIC_URLS = {
			
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**",
			"/v3/api-docs"
//			HttpMethod.POST,"/users/**"
	};*/

	@Autowired
	public UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		
		//		Basic Authentication
										httpSecurity.csrf()
										.disable()
										.cors()
										.disable()
										.authorizeRequests()
										.requestMatchers("/auth/login")	// for Login the User			
										 .permitAll()
										
//										 .requestMatchers(PUBLIC_URLS)
//										 .permitAll()
										 .requestMatchers(HttpMethod.POST,"/users/**") //for Registering the User
										 .permitAll()
										 .requestMatchers(HttpMethod.DELETE,"/users/**")
										 .hasRole("ADMIN")
										 .anyRequest()
										 .authenticated()
										 .and()
										 .exceptionHandling()
										 .authenticationEntryPoint(authenticationEntryPoint)
										 .and()
										 .sessionManagement()
										 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
										
								httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	
		return httpSecurity.build();

		
		/*
		httpSecurity.csrf(csrf->csrf.disable())
		.cors(cors->cors.disable())
	
		.authorizeHttpRequests(auth -> auth
			    // .requestMatchers(PUBLIC_URLS).permitAll()
		
			    .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()  // for login the user...
			    .requestMatchers(HttpMethod.POST, "/users/**").permitAll()// for register the user...
			    //.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
			    .anyRequest().authenticated())
				.exceptionHandling(ex-> ex.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		httpSecurity.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
					return httpSecurity.build();*/
		
	}

		 
	

}
