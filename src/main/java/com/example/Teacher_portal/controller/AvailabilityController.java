package com.example.Teacher_portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Teacher_portal.model.Availability;
import com.example.Teacher_portal.request.RequestAvailability;
import com.example.Teacher_portal.service.AvailabilityService;


@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {
	
	@Autowired
	private AvailabilityService availabilityService;

	
	@PostMapping("/user/{userId}")
    public ResponseEntity<Availability> createUserAvailability(@RequestHeader("Authorization") String jwt, @PathVariable Long userId, @RequestBody RequestAvailability request) {
        Availability availability = availabilityService.createUserAvailability(userId, request);
        return ResponseEntity.ok(availability);
    }
	 
	 
	/*  public List<Availability> getTeacherAvailability(@PathVariable Long userId) {
	        return availabilityService.getAvailability(userId);
	 }
	  
	 
	/*    
	   @PutMapping("/{teacherId}/availability/{id}")
	    @PreAuthorize("hasAuthority('UPDATE_TEACHER_AVAILABILITY')")
	    public TeacherAvailability updateTeacherAvailability(@PathVariable Long teacherId, 
	            @PathVariable Long id, 
	            @RequestBody TeacherAvailability availability) {
	        return service.updateTeacherAvailability(id, availability);
	    }
	    
	    @DeleteMapping("/{teacherId}/availability/{id}")
	    @PreAuthorize("hasAuthority('DELETE_TEACHER_AVAILABILITY')")
	    public void deleteTeacherAvailability(@PathVariable Long teacherId, 
	            @PathVariable Long id) {
	        service.deleteTeacherAvailability(id);
	    }

*/
}
