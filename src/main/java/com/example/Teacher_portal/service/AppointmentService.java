package com.example.Teacher_portal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.coyote.BadRequestException;
import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.exception.ResourceNotFoundException;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.request.AppointmentRequest;
import com.example.Teacher_portal.request.RescheduleRequest;

public interface AppointmentService {

	// create
	public Appointments createAppointment(Long availabilityId, String jwt) throws BadRequestException, UserException, ResourceNotFoundException;

	// confirm
	public Appointments confirmAppointment(Long appointmentId) throws ResourceNotFoundException;

	// decline
	public Appointments declineAppointment(Long appointmentId) throws BadRequestException;

	// reschedule
	public Appointments rescheduleAppointment(Long appointmentId, Long newAvailabilitySlotId) throws ResourceNotFoundException;

	// delete
	public void deleteAppointment(Long appointmentId) throws BadRequestException;

	public List<Appointments> getAllAppointments();

	public List<Appointments> getAppointmentsByUserId(Long userId);
	
    List<Appointments> getAppointmentsWithinNextHour(LocalDateTime currentTime);

}
