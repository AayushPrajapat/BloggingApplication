package com.blog.application.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.blog.application.dtos.UserDto;
import com.blog.application.entities.Role;
import com.blog.application.entities.User;
import com.blog.application.exception.ResourceNotFoundException;
import com.blog.application.repositories.RoleRepository;
import com.blog.application.repositories.UserRepository;
import com.blog.application.services.UserService;
import com.blog.application.utils.AppContants;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${role.normal.id}")
	private int normalRoleId;
	
	
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = mapper.map(userDto, User.class);
		
//	 	encoded formate set password
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		user.setCreatedDate(new Date());
		
		user.setUpdatedDate(new Date());
		
//		specify the role ---way of old 
		//Role role = this.roleRepository.findById(AppContants.NORMAL_USER).get();
		//user.getRoles().add(role);
		
//		specify the role--way of latest
		Role role = this.roleRepository.findById(normalRoleId).get();
		user.getRoles().add(role);
		
		User savedUser = this.userRepository.save(user);
		
		return mapper.map(savedUser,UserDto.class);
	}
	
	@Override
	public UserDto createUser(UserDto userDto) {

		User user = this.dtoToEntity(userDto);
		user.setCreatedDate(new Date());
		//user.setUpdatedDate(new Date());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		
		User savedUser = this.userRepository.save(user);

		return this.entityToDto(savedUser);
	}

	
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setDescription(userDto.getDescription());
		user.setUpdatedDate(new Date());

		User updatedUser = this.userRepository.save(user);

		return this.entityToDto(updatedUser);
	}

	@Override
	public void deleteUser(Integer userId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepository.delete(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> getAllUsers = this.userRepository.findAll();
		List<UserDto> userDtos = getAllUsers.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		return entityToDto(user);
	}

	@Override
	public UserDto getUserByEmail(String email) {
			User userEmail = this.userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Email not Found!!"));
		return entityToDto(userEmail);
	}




	
	public User dtoToEntity(UserDto userDto) {
	
		User user = mapper.map(userDto, User.class);
		
		
		/*
		User user = new User();
		 * 
		 * user.setUserId(userDto.getUserId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setDescription(userDto.getDescription());
		user.setCreatedDate(userDto.getCreatedDate());
		user.setUpdatedDate(userDto.getUpdatedDate());*/

		return user;

	}

	public UserDto entityToDto(User user) {
		
		UserDto userDto = mapper.map(user, UserDto.class);
		
		/*
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setDescription(user.getDescription());
		userDto.setCreatedDate(user.getCreatedDate());
		userDto.setUpdatedDate(user.getUpdatedDate());*/

		return userDto;
	}

	
}
