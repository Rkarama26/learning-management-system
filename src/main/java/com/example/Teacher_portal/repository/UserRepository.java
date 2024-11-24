package com.example.Teacher_portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Teacher_portal.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	

	public User findByEmail(String email);
	

}
