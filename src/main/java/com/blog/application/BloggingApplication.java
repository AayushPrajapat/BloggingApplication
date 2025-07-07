package com.blog.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.entities.Role;
import com.blog.application.repositories.RoleRepository;
import com.blog.application.utils.AppContants;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Value("${role.normal.id}")
	private int noramlRoleId;
	
	@Value("${role.admin.id}")
	private int adminRoleId;
	
	@Override
	public void run(String... args) throws Exception {
		
//		System.out.println(passwordEncoder.encode("45445"));
		
		/*
		try {
			Role role = new Role();
			role.setRoleId(adminRoleId);
			role.setRoleName("ADMIN_USER");
			
			Role role2 = new Role();
			role2.setRoleId(noramlRoleId);
			role2.setRoleName("NORMAL_USER");
			
			Role savedAdmin = roleRepository.save(role);
			Role savedNormal = roleRepository.save(role2);
			
			System.out.println(savedAdmin+"     "+savedNormal);*/
			
			/*
			List<Role> roles = roleRepository.findAll();
			
			roles.forEach(r->{
				System.out.println(r.getRoleName());
			});*/
			/*
		} catch (Exception e) {
			e.printStackTrace();
		}
*/
		
	}

}




