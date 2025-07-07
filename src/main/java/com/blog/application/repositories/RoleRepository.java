package com.blog.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.application.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

}
