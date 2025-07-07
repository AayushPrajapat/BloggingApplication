package com.blog.application.services;

import java.util.List;
import com.blog.application.dtos.PostDto;
import com.blog.application.utils.PostResponse;

public interface PostService {

//	create
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

//	update
	public PostDto updatePost(PostDto postDto, Integer postId);

//  delete
	void deletePost(Integer postId);

//	getAll
	public PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir);

//	getById
	public PostDto getByPostId(Integer postId);

//	get All posts by Category
	public List<PostDto> getPostsByCategory(Integer categoryId);

//	get All Posts by user
	public List<PostDto> getPostsByUser(Integer userId);
	
//	search posts
	public List<PostDto> searchPosts(String keyword);

}
