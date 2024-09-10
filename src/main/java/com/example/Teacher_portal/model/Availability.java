package com.example.Teacher_portal.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Availability {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

    private String dayOfWeek;
    
    private LocalTime startTime;
    
    private LocalTime endTime;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Availability() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Availability(Long id, String dayOfWeek, LocalTime startTime, LocalTime endTime, User user) {
		super();
		this.id = id;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.user = user;
	}

    
    
}