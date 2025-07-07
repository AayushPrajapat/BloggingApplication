package com.blog.application.utils;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponseMessage {
	
	private String message;
	private HttpStatus status;
	private boolean success;

}
