package com.example.Teacher_portal.controller;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.InvalidJwtTokenException;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.request.ChangePasswordRequest;
import com.example.Teacher_portal.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
    
	//get userProfile
	@GetMapping("/profile")
	//@Secured({"ROLE_STUDENT", "ROLE_TEACHER"})
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		
		System.out.println("getUserProfileHandler");
		System.out.println("JWT Token: " + jwt);
		
	try {
		User user = userService.findUserprofileByJwt(jwt);
	
		return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
	}catch(InvalidJwtTokenException e) {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
		}
	
	
	//update profile
	@PutMapping("/updatedProfile")
	public ResponseEntity<User> updateProfileHandler(@RequestHeader("Authorization") String jwt, @RequestBody User updatedUser) throws UserException {
		
		System.out.println("JWT Token: " + jwt);

	    User updatedUserProfile = userService.updateUserProfile(jwt, updatedUser);
	    
	    return new ResponseEntity<>(updatedUserProfile, HttpStatus.ACCEPTED);
	}
	
	
	// Delete profile by ID
	@DeleteMapping("/deleteProfile/{id}")
	public ResponseEntity<String> deleteProfileHandler(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws UserException {
	    
	    System.out.println("User ID: " + id);

	    userService.deleteUserProfile(id);
	    
	    return new ResponseEntity<>("Profile deleted successfully", HttpStatus.ACCEPTED);
	}	
	
	
    // change password
	@PostMapping("/changePassword")
    public ResponseEntity<String> changePasswordHandler(@RequestHeader("Authorization") String jwt, @RequestBody ChangePasswordRequest request) {
		
	
		//System.out.println("JWT Token: " + jwt);
		
        try {
            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            	
                return ResponseEntity.badRequest().body("New password and confirm password do not match");
                
            }
            userService.changePassword(jwt, request.getOldPassword(), request.getNewPassword());
            
            return ResponseEntity.ok("Password changed successfully");
            
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
	
}
	
	

