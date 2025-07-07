package com.blog.application.services;

import java.util.List;

import com.blog.application.dtos.UserDto;

public interface UserService {

//	new user register
	public UserDto registerNewUser(UserDto userDto);
	
//	create
	UserDto createUser(UserDto userDto);

//	update
	UserDto updateUser(UserDto userDto, Integer userId);

//	delete
	void deleteUser(Integer userId);

//	get All
	List<UserDto> getAllUsers();

//	get Single by Id
	UserDto getUserById(Integer userId);

//	get single by email
	UserDto getUserByEmail(String email);

}
