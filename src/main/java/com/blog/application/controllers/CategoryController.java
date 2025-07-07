package com.blog.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.application.dtos.CategoryDto;
import com.blog.application.services.CategoryService;
import com.blog.application.utils.ApiResponseMessage;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

//	create
	@PostMapping
	public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategoryDto,HttpStatus.CREATED);
	}
//	update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		return  new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
//	delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseMessage> delete(@PathVariable Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
		.message("Category SuccessFully!! Deleted..")
		.status(HttpStatus.OK)
		.success(true)
		.build();
		return ResponseEntity.ok(apiResponseMessage);
	}
//	getAll
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAll(){
		List<CategoryDto> categoryDtos = this.categoryService.getAllCategory();
		return ResponseEntity.ok(categoryDtos);
	}
//	getById
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getByIdCategory(@PathVariable Integer categoryId){
		CategoryDto categoryDto = this.categoryService.getById(categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}
	
}
