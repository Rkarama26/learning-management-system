package com.example.Teacher_portal.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Teacher_portal.exception.AvailabilityNotFoundException;
import com.example.Teacher_portal.model.Availability;
import com.example.Teacher_portal.model.User;
import com.example.Teacher_portal.repository.AvailabilityRepository;
import com.example.Teacher_portal.repository.UserRepository;
import com.example.Teacher_portal.request.ReqAvailability;

@Service
public class AvailabilityService {

	@Autowired
	private AvailabilityRepository availRepository;

	@Autowired
	private UserRepository userRepository;

	//create
	public Availability createUserAvailability(Long userId, Availability available) {
		Availability availability = new Availability();
		availability.setUser(userRepository.findById(userId).orElseThrow());
		availability.setDayOfWeek(available.getDayOfWeek());
		availability.setEndTime(available.getEndTime());
		availability.setStartTime(available.getStartTime());
		return availRepository.save(availability);
	}

	//get 
	public List<Availability> getUserAvailability(Long userId) {
		List<Availability> availabilities = availRepository.findByUser_Id(userId);
		if (availabilities == null || availabilities.isEmpty()) {
			throw new AvailabilityNotFoundException("No availability found for user with ID \" + userId");
		}

		return availabilities;
	}
     //delete
	public void deleteAvailability(Long id) {

		Availability availability = availRepository.findById(id).orElseThrow();

		availRepository.delete(availability);
	}
	
	//update
	public Availability updateAvailability(Long id, ReqAvailability req) {
        Availability availability = availRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Availability not found"
        ));
 
        availability.setDayOfWeek(req.getDayOfWeek()); 
        availability.setStartTime(req.getStartTime());
        availability.setEndTime(req.getEndTime());
        
        return availRepository.save(availability);
    }

}
