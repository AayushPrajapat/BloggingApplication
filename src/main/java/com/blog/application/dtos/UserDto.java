package com.blog.application.dtos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto {

	private int userId;

	@NotEmpty//@notnull + @notempty
	@Size(min = 4,max = 20,message = "username must be min of 4 characters and max 20 characters!!")
	private String name;
	
	@Email(message = "Email Address is not Valid!!")
	private String email;
	
	@NotEmpty
	@Size(min = 3,max = 10,message = "Password Must be min of 3 characters and max of 10 characters!!")
	private String password;
	
	@NotNull
	private String description;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private Set<RoleDto> roles = new HashSet<>();
	
	
}
