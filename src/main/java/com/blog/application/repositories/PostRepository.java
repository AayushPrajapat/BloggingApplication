package com.blog.application.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.application.entities.Category;
import com.blog.application.entities.Post;
import com.blog.application.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
//	kisi user ke Sare post fetch krna
	List<Post> findByUser(User user);

//	kisi categoru ke sare post fetch krna
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);

}
