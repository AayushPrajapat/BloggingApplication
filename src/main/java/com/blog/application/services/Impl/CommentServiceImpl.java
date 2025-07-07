package com.blog.application.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blog.application.dtos.CommentDto;
import com.blog.application.dtos.PostDto;
import com.blog.application.dtos.UserDto;
import com.blog.application.entities.Comment;
import com.blog.application.entities.Post;
import com.blog.application.entities.User;
import com.blog.application.exception.ResourceNotFoundException;
import com.blog.application.repositories.CommentRepository;
import com.blog.application.services.CommentService;
import com.blog.application.services.PostService;
import com.blog.application.services.UserService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CommentDto create(CommentDto commentDto, Integer postId,Integer userId) {
		
		
	PostDto postDto = this.postService.getByPostId(postId);
	
	UserDto userDto = this.userService.getUserById(userId);
	
	
	Comment comment = this.mapper.map(commentDto, Comment.class);
	
	comment.setPost(this.mapper.map(postDto, Post.class));
	comment.setUser(this.mapper.map(userDto,User.class));
	
	Comment savedComment = this.commentRepository.save(comment);
	
		return this.mapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void delete(Integer commentId) {
		
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","commentId", commentId));
		
		this.commentRepository.delete(comment);
		
	}
	

}
