package com.blog.application.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.blog.application.dtos.PostDto;
import com.blog.application.services.FileService;
import com.blog.application.services.PostService;
import com.blog.application.utils.ApiResponseMessage;
import com.blog.application.utils.AppContants;
import com.blog.application.utils.PostResponse;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	public String path;
	
//	create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> create(
		@Valid @RequestBody PostDto postDto
			,@PathVariable Integer userId
			,@PathVariable Integer categoryId){
		PostDto createdPost = this.postService.createPost(postDto,userId,categoryId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
//	update
	@PutMapping("/{postId}/posts")
	public ResponseEntity<PostDto> update(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
//	delete
	@DeleteMapping("/{postId}/posts")
	public ResponseEntity<ApiResponseMessage> delete(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
		.message("SuccessFully Deleted!! Post...")
		.success(true)
		.status(HttpStatus.OK)
		.build();
		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage,HttpStatus.OK);
	}
//	getALL
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
					@RequestParam(value ="pageNumber",defaultValue = AppContants.PAGE_NUMBER,required = false) int pageNumber,//page no. 0 1 2 3 4
					@RequestParam(value = "pageSize",defaultValue = AppContants.PAGE_SIZE,required = false) int pageSize,//element show 
					@RequestParam(value = "sortBy",defaultValue = AppContants.SORT_BY,required = false) String sortBy,
					@RequestParam(value = "sortDir",defaultValue = AppContants.SORT_DIR,required = false) String sortDir//byDefault sort Ascending Order..	
					){
	 PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
//	getById
	
	@GetMapping("/{postId}/posts")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getByPostId(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
//	get All Posts by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		List<PostDto> postsByUser = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);
	}
//	get All Posts by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto> postByCategory = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postByCategory,HttpStatus.OK);
	}
//	search
	@GetMapping("/search/{keyword}/posts")
	public ResponseEntity<List<PostDto>> getPostBySearch(@PathVariable String keyword){
		List<PostDto> searchPosts = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
	}
//	upload Image
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> PostImageUpload(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		PostDto postDto = this.postService.getByPostId(postId);
		
		String uploadImage = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(uploadImage);
		
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);	
	}
	
//	serve Image
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException
				{
		InputStream inputStream = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream,response.getOutputStream());
	}
	
	
}
