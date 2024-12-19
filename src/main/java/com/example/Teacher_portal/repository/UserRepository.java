package com.example.Teacher_portal.repository;

import com.example.Teacher_portal.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    public User findByEmail(String email);

}
