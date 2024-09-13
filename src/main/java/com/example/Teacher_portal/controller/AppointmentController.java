package com.example.Teacher_portal.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Teacher_portal.model.Appointments;
import com.example.Teacher_portal.request.AppointmentRequest;
import com.example.Teacher_portal.service.AppointmentService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;

	 @PostMapping("/book")
	    public ResponseEntity<Appointments> bookAppointmentHandler(@RequestBody AppointmentRequest request) throws BadRequestException {
	        return ResponseEntity.ok(appointmentService.createAppointment(request));
	    }
	
}
