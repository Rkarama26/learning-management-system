package com.example.Teacher_portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Teacher_portal.config.JwtProvider;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.model.User;
import com.example.Teacher_portal.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider Jwtprovider;
	@Autowired
    private PasswordEncoder passwordEncoder;

	
	// finding userProfile by JWT 
	public User findUserprofileByJwt(String jwt) throws UserException {
		
		String email = Jwtprovider.getEmailFromToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UserException("User not found");
		}
		
		return user;
	}

	
    // update profile
	public User updateUserProfile(String jwt, User updatedUser) throws UserException {
        User user = findUserprofileByJwt(jwt);
        
        // Update user profile fields
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
      
        // Save updated user profile
        User saveUser = userRepository.save(user);
        
        return saveUser;
    }

	
	// Delete userProfile by ID
    public void deleteUserProfile(Long id) throws UserException {
		    
		    System.out.println(" in service User ID: " + id);

		    User user = userRepository.findById(id).orElseThrow();
		    
		    userRepository.delete(user);
		}

    
	// change password	
	public void changePassword(String jwt, String oldPassword, String newPassword) throws UserException {
       
		//System.out.println(" in service JWT Token: " + jwt);

		
        User user = findUserprofileByJwt(jwt);
        
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new UserException("Old password is incorrect");
        }
        
        // Update the user's password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


	
	
	

}
