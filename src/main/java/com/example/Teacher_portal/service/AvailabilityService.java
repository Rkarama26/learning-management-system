package com.example.Teacher_portal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Teacher_portal.model.Availability;
import com.example.Teacher_portal.repository.AvailabilityRepository;
import com.example.Teacher_portal.repository.UserRepository;
import com.example.Teacher_portal.request.RequestAvailability;

@Service
public class AvailabilityService {
	
	@Autowired
	private  AvailabilityRepository availRepository;
	
	@Autowired
	private UserRepository userRepository;


	 public Availability createUserAvailability(Long userId, RequestAvailability request) {
	        Availability availability = new Availability();
	        availability.setUser(userRepository.findById(userId).orElseThrow()); 
	        availability.setDayOfWeek(request.getDayOfWeek());
	        availability.setEndTime(request.getEndTime());
	        availability.setStartTime(request.getStartTime());
	        return availRepository.save(availability);
	    }


	



	 

	
	
	
   
}
