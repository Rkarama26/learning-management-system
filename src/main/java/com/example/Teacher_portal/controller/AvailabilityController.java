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

import com.example.Teacher_portal.Entity.Availability;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.request.AvailabilityReq;
import com.example.Teacher_portal.service.AvailabilityService;
import com.example.Teacher_portal.service.UserService;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

	@Autowired
	private AvailabilityService availabilityService;
	
	@Autowired
	private UserService userService;

	
	
	@GetMapping("/user")
	public ResponseEntity<List<Availability>> getUserAvailability(@RequestHeader("Authorization") String jwt) throws UserException {
		
		User user = userService.findUserprofileByJwt(jwt);
		Long userId = user.getId();
		
		List<Availability> availabilities = availabilityService.getUserAvailability(userId);
		return ResponseEntity.ok(availabilities);
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<Availability> createUserAvailability(@RequestHeader("Authorization") String jwt,
			@RequestBody Availability available) throws Exception {
		
		Availability availability = availabilityService.createUserAvailability(jwt, available);
		return ResponseEntity.ok(availability);
	}
	

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAvailability(@RequestHeader("Authorization") String jwt, @PathVariable Long id) {
		availabilityService.deleteAvailability(id);
		return new ResponseEntity<>("Deleted successfully", HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/update/{id}")
    public ResponseEntity<Availability> updateAvailability(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody AvailabilityReq req) {
        Availability updatedAvailability = availabilityService.updateAvailability(id, req);
        return ResponseEntity.ok(updatedAvailability);
    }
	
	@GetMapping("/all")
	public ResponseEntity<List<Availability>> getAllAvailableSlots(@RequestHeader("Authorization") String jwt){
		
		List<Availability> available = availabilityService.getAllAvailableSlots();
				return ResponseEntity.ok(available);
	}
    
	
}
