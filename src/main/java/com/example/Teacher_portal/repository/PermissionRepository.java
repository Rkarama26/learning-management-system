package com.example.Teacher_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Teacher_portal.model.Role.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{

	
	//List<Permission> findByRoleId(Long roleId);

	@Query("SELECT r FROM Role r WHERE r.name = :name")
	Permission findByName(@Param("name") String name);
}
