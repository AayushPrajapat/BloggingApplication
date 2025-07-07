package com.blog.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blog.application.entities.User;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//
//	@Query(name = "select * from user where email=:nm",nativeQuery = true)
//	User getSingleUserByEmail(@Param("nm") String email);
//	
	
	Optional<User> findByEmail(String email);
	
	
}
