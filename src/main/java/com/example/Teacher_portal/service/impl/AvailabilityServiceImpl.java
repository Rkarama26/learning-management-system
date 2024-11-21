package com.example.Teacher_portal.service.impl;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Teacher_portal.Entity.Availability;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.AvailabilityNotFoundException;
import com.example.Teacher_portal.exception.EndTimeBeforeStartTimeException;
import com.example.Teacher_portal.exception.SlotAlreadyPresentException;
import com.example.Teacher_portal.exception.StartTimeBeforeCurrentTimeException;
import com.example.Teacher_portal.repository.AvailRepository;
import com.example.Teacher_portal.repository.UserRepository;
import com.example.Teacher_portal.request.ReqAvailability;
import com.example.Teacher_portal.service.AvailabilityService;
import com.example.Teacher_portal.service.UserService;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AvailRepository availRepository;
	@Autowired
	private UserService userService;

	// create
	public Availability createUserAvailability(String jwt, Availability available) throws Exception {

		User user = userService.findUserprofileByJwt(jwt);
		Long userId = user.getId();

		Availability existingAvailability = availRepository.findByUserAndStartTimeAndEndTime(user,
				available.getStartTime(), available.getEndTime());

		if (existingAvailability != null) {
			throw new SlotAlreadyPresentException("Slot already present for teacher " + user.getFirstName() + " on "
					+ " at " + available.getStartTime());

		} else {

			// Validate start time and end time
			Instant currentTime = Instant.now();
			if (available.getStartTime().atZone(ZoneOffset.systemDefault()).toInstant().isBefore(currentTime)) {
				throw new StartTimeBeforeCurrentTimeException("Start time must be after the current time");
			}
			if (available.getEndTime().atZone(ZoneOffset.systemDefault()).toInstant()
					.isBefore(available.getStartTime().atZone(ZoneOffset.systemDefault()).toInstant())) {
				throw new EndTimeBeforeStartTimeException("End time must be after the start time");
			}

			Availability availability = new Availability();
			availability.setUser(user);
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
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Slot Available"));

			availability.setStartTime(req.getStartTime());
			availability.setEndTime(req.getEndTime());

			return availRepository.save(availability);
		}
	}

	@Override
	public List<Availability> getAllAvailableSlots() {
		List<Availability> available = availRepository.findAll();
		return available;
	}

}
