package com.example.Teacher_portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Teacher_portal.Entity.Availability;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.AvailabilityNotFoundException;
import com.example.Teacher_portal.exception.SlotAlreadyPresentException;
import com.example.Teacher_portal.repository.AvailRepository;
import com.example.Teacher_portal.repository.UserRepository;
import com.example.Teacher_portal.request.ReqAvailability;
import com.example.Teacher_portal.service.AvailabilityService;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AvailRepository availRepository;

	// create
	public Availability createUserAvailability(Long userId, Availability available) {
		User user = userRepository.findById(userId).orElseThrow();
		Availability existingAvailability = availRepository.findByUserAndDayOfWeekAndStartTimeAndEndTime(user,
				available.getDayOfWeek(), available.getStartTime(), available.getEndTime());

		if (existingAvailability != null) {
            throw new SlotAlreadyPresentException("Slot already present for teacher " + user.getFirstName() + " on " + available.getDayOfWeek() + " at " + available.getStartTime());

		} else {

			Availability availability = new Availability();
			availability.setUser(userRepository.findById(userId).orElseThrow());
			availability.setDayOfWeek(available.getDayOfWeek());
			availability.setEndTime(available.getEndTime());
			availability.setStartTime(available.getStartTime());
			return availRepository.save(availability);
		}
	}

	// get
	public List<Availability> getUserAvailability(Long userId) {
		List<Availability> availabilities = availRepository.findByUser_Id(userId);
		if (availabilities == null || availabilities.isEmpty()) {
			throw new AvailabilityNotFoundException("No availability found for user with ID \" + userId");
		}

		return availabilities;
	}

	// delete
	public void deleteAvailability(Long id) {

		Availability availability = availRepository.findById(id).orElseThrow();

		availRepository.delete(availability);
	}

	// update
	public Availability updateAvailability(Long id, ReqAvailability req) {
		{
			Availability availability = availRepository.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Availability not found"));

			availability.setDayOfWeek(req.getDayOfWeek());
			availability.setStartTime(req.getStartTime());
			availability.setEndTime(req.getEndTime());

			return availRepository.save(availability);
		}
	}

}
