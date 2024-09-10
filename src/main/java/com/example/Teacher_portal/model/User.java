package com.example.Teacher_portal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    private String firstName;
    
    private String lastName;

    private String password;
   
  //  @Enumerated(EnumType.STRING)
  //  private Role role;  //"STUDENT" or "TEACHER"
    
    private String email;
    
    private String phoneNumber;
    
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Availability> availability;
    

	public User(Long id, String firstName, String lastName, String password, String email,
			String phoneNumber, List<Availability> availability) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.availability = availability;
	}


	public List<Availability> getAvailability() {
		return availability;
	}


	public void setAvailability(List<Availability> availability) {
		this.availability = availability;
	}

	public User() {
		// NO ARGS CONTRUCTOR
	}

	
	public User(Long id, String firstName, String lastName, String password, String email, String phoneNumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
    
    
    
    
    
    
    

}
