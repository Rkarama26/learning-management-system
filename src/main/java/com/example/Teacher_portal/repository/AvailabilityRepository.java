package com.example.Teacher_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Teacher_portal.model.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

	 @EntityGraph(attributePaths = "user")
	    List<Availability> findByUser_Id(Long userId);
}
