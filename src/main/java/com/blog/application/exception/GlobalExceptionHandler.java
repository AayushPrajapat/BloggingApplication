package com.blog.application.exception;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.blog.application.utils.ApiResponseMessage;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException){
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
		.message(resourceNotFoundException.getMessage())
		.status(HttpStatus.NOT_FOUND)
		.success(false)
		.build();
		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage,HttpStatus.NOT_FOUND);
	}
	/*
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseMessage> globalException(Exception exception){
		log.info("Exception Invoked!!");
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
		.message(exception.getMessage())
		.status(HttpStatus.INTERNAL_SERVER_ERROR)
		.success(true)
		.build();
		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage,HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
		Map<String,String> resp = new HashMap<>();
		methodArgumentNotValidException.getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String defaultMessage = error.getDefaultMessage();
			resp.put(fieldName, defaultMessage);
		});
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}

}
