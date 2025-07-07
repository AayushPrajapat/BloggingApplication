package com.blog.application.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(length = 100,nullable = false)
	private String title;
	
	@Column(length = 10000,nullable = false)
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
//	kis user ne post add kiya
	@ManyToOne
	@JoinColumn(name = "user_Id")
	private User user;
	
//	kis category mai ye post add huya
	@ManyToOne
	@JoinColumn(name = "category_Id")
	private Category category;
	
	@OneToMany(mappedBy = "post")
	private Set<Comment> comments = new HashSet<>();

}
