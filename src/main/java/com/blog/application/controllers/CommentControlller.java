package com.blog.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.application.dtos.CommentDto;
import com.blog.application.services.CommentService;
import com.blog.application.utils.ApiResponseMessage;

@RestController
@RequestMapping("/api/")
public class CommentControlller {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/user/{userId}/comments")
	public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto,
			@PathVariable Integer postId,
			@PathVariable Integer userId){
		CommentDto createdComment = this.commentService.create(commentDto, postId,userId);
		return new ResponseEntity<CommentDto>(createdComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponseMessage> delete(@PathVariable Integer commentId){
		this.commentService.delete(commentId);
		
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
		.message("Comment SuccessFully!! Deleted...")
		.status(HttpStatus.OK)
		.success(true)
		.build();
		
		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage,HttpStatus.OK);
	}
	
}
