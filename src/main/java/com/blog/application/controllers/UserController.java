package com.blog.application.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.application.dtos.UserDto;
import com.blog.application.services.UserService;
import com.blog.application.utils.ApiResponseMessage;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
//@EnableMethodSecurity(prePostEnabled = true)
public class UserController {
	
	@Autowired
	private UserService userService;
	
//	register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registerUserDto = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registerUserDto,HttpStatus.CREATED); 
	}
	
//	create
	@PostMapping
	public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto){
		UserDto createUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUser,HttpStatus.CREATED);
	}
	
//	update
	//@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto,
			@PathVariable Integer userId){
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updatedUser,HttpStatus.OK);
	}
//	Delete can only Admin
//	delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> delete(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
		.message("User SuccessFully!! Deleted")
		.status(HttpStatus.OK)
		.success(true)
		.build();
		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage,HttpStatus.OK);
	}
	
//	getAll
	@GetMapping
	public ResponseEntity<List<UserDto>> getAll(){
		List<UserDto> allUsers = this.userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(allUsers,HttpStatus.OK);
	}
//	getSingleById
	@GetMapping("/singleuser/{userId}")
	public ResponseEntity<UserDto> getById(@PathVariable Integer userId){
		UserDto userDto = this.userService.getUserById(userId);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	
//	getSingleByEmail
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getByEmail(@PathVariable String email){
		UserDto userDto = this.userService.getUserByEmail(email);
		return ResponseEntity.ok(userDto);
		
	}
	
}
