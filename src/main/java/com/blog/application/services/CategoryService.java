package com.blog.application.services;

import java.util.List;

import com.blog.application.dtos.CategoryDto;

public interface CategoryService {

//	create
	public CategoryDto createCategory(CategoryDto categoryDto);

//	update
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

//	delete
	void deleteCategory(Integer categoryId);

//	get All
	public List<CategoryDto> getAllCategory();

//	get By Id
	public CategoryDto getById(Integer categoryId);
}
