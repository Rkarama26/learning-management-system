package com.example.Teacher_portal.service;

import java.util.List;

import com.example.Teacher_portal.Entity.Availability;
import com.example.Teacher_portal.request.AvailabilityReq;


public interface AvailabilityService {
	

	// create
	public Availability createUserAvailability(String jwt, Availability available) throws Exception;

	// get
	public List<Availability> getUserAvailability(Long userId);

	// delete
	public void deleteAvailability(Long id);

	// update
	public Availability updateAvailability(Long id, AvailabilityReq req);

	public List<Availability> getAllAvailableSlots();

}
