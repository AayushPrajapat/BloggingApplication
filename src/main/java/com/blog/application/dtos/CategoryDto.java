package com.blog.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	
	//@NotEmpty//@notnull + @notempty
	@NotBlank
	@Size(min = 4,max = 20,message = "title must be min of 4 characters and max 20 characters!!")
	private String title;

	@NotBlank
	@Size(min = 10,message = "Description atleast must be Maximum 10 characters!!")
	private String description;
}
