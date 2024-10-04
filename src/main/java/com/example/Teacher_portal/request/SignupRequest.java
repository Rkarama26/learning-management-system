package com.example.Teacher_portal.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

	 private String firstName;
	    private String lastName;
	    private String password;
	    private String email;
	    private String phoneNumber;
	    private String role; // or Role role; if you want to receive the entire Role object

}
