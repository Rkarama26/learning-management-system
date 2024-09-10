package com.example.Teacher_portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Teacher_portal.model.Availability;
import com.example.Teacher_portal.request.ReqAvailability;
import com.example.Teacher_portal.service.AvailabilityService;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

	@Autowired
	private AvailabilityService availabilityService;

	
	@PostMapping("/create/{userId}")
	public ResponseEntity<Availability> createUserAvailability(@RequestHeader("Authorization") String jwt,
			@PathVariable Long userId, @RequestBody Availability available) {
		Availability availability = availabilityService.createUserAvailability(userId, available);
		return ResponseEntity.ok(availability);
	}
	

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Availability>> getUserAvailability(@RequestHeader("Authorization") String jwt,
			@PathVariable Long userId) {
		List<Availability> availabilities = availabilityService.getUserAvailability(userId);
		return ResponseEntity.ok(availabilities);
	}
	

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAvailability(@RequestHeader("Authorization") String jwt, @PathVariable Long id) {
		availabilityService.deleteAvailability(id);
		return new ResponseEntity<>("Deleted successfully", HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/update/{id}")
    public ResponseEntity<Availability> updateAvailability(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody ReqAvailability req) {
      
		
        Availability updatedAvailability = availabilityService.updateAvailability(id, req);
        return ResponseEntity.ok(updatedAvailability);
    }
    
	
}
