package com.blog.application.services;

import com.blog.application.dtos.CommentDto;

public interface CommentService {

//	create
	public CommentDto create(CommentDto commentDto, Integer postId,Integer userId);

//	delete
	void delete(Integer commentId);

}
