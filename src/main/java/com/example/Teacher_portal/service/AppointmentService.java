package com.example.Teacher_portal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.coyote.BadRequestException;
import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.request.AppointmentRequest;
import com.example.Teacher_portal.request.RescheduleRequest;

public interface AppointmentService {

	// create
	public Appointments createAppointment(AppointmentRequest request) throws BadRequestException;

	// confirm
	public Appointments confirmAppointment(Long appointmentId);

	// decline
	public Appointments declineAppointment(Long appointmentId) throws BadRequestException;

	// reschedule
	public Appointments rescheduleAppointment(Long appointmentId, RescheduleRequest request);

	// delete
	public void deleteAppointment(Long appointmentId) throws BadRequestException;

	public List<Appointments> getAllAppointments();

	public List<Appointments> getAppointmentsByUserId(Long userId);
	
    List<Appointments> getAppointmentsWithinNextHour(LocalDateTime currentTime);

}
