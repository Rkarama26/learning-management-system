package com.example.Teacher_portal.service.impl;

import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Teacher_portal.Entity.AppointmentStatus;
import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.Entity.Availability;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.ResourceNotFoundException;
import com.example.Teacher_portal.exception.SlotAlreadyBookedException;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.repository.AppointmentRepository;
import com.example.Teacher_portal.repository.AvailRepository;
import com.example.Teacher_portal.repository.UserRepository;
import com.example.Teacher_portal.service.AppointmentService;
import com.example.Teacher_portal.service.UserService;
import com.example.Teacher_portal.service.reminder.EmailService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AvailRepository availRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	// create
	@Transactional
	public Appointments createAppointment(Long availabilityId, String jwt)
			throws BadRequestException, UserException, ResourceNotFoundException {

		Availability slot = availRepository.findById(availabilityId)
				.orElseThrow(() -> new ResourceNotFoundException("Slot not found"));

		if (slot.isBooked()) {
			throw new SlotAlreadyBookedException("Slot already booked for teacher ");

		}
		User student = userService.findUserprofileByJwt(jwt);
		User teacher = slot.getUser();

		// Create new appointment
		Appointments appointment = new Appointments();
		appointment.setAvailability(slot);
		appointment.setStudent(student);
		appointment.setBookingTime(LocalDateTime.now());
		appointment.setStatus(AppointmentStatus.PENDING);
		appointment.setStartTime(slot.getStartTime());
		appointment.setEndTime(slot.getEndTime());
		appointment.setTeacher(teacher);

		// slot.setBooked(true);

		Appointments savedAppointment = appointmentRepository.save(appointment);

		// Send email reminder to the teacher
		emailService.sendAppointmentReminder(teacher, savedAppointment);
		// emailService.sendEmail("rv262003@gmail.com", "subject", "msg");
		return savedAppointment;
	}

	// Reminders
	@Async
	public void sendReminderToTeacher(User teacher, Appointments appointment) {
		emailService.sendAppointmentReminder(teacher, appointment);
	}

	// confirm
	@Transactional
	public Appointments confirmAppointment(Long appointmentId) throws ResourceNotFoundException {

		Appointments appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

		if (appointment.getStatus() == AppointmentStatus.CONFIRMED) {
			throw new IllegalStateException("Appointment is already confirmed");
		}

		appointment.setStatus(AppointmentStatus.CONFIRMED);

		Availability availability = appointment.getAvailability();

		if (availability != null) {

			availability.setBooked(true);
			availRepository.save(availability);
		}

		return appointmentRepository.save(appointment);
	}

	// decline
	@Transactional
	public Appointments declineAppointment(Long appointmentId) throws BadRequestException {
		// deleteAppointment(appointmentId);
		Appointments appointment = appointmentRepository.findById(appointmentId).orElseThrow();

		if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
			throw new IllegalStateException("Appointment is already declined.");
		}

		// Update the appointment status to 'DECLINED'
		appointment.setStatus(AppointmentStatus.CANCELLED);

		// Optionally, you can release the slot by setting isBooked to false
		Availability availability = appointment.getAvailability();
		if (availability != null && availability.isBooked()) {
			availability.setBooked(false); // Release the slot
			availRepository.save(availability); // Save the updated slot
		}

		return appointmentRepository.save(appointment);
	}

	// reschedule
	public Appointments rescheduleAppointment(Long appointmentId, Long newAvailabilitySlotId) throws ResourceNotFoundException {

		Appointments appointment = appointmentRepository.findById(appointmentId).orElseThrow();

		// Free the old availability slot
		Availability oldAvailabilitySlot = appointment.getAvailability();
		oldAvailabilitySlot.setBooked(false);
		availRepository.save(oldAvailabilitySlot);

		Availability newAvailabilitySlot = availRepository.findById(newAvailabilitySlotId)
				.orElseThrow(() -> new ResourceNotFoundException("new slot not found"));

		
		appointment.setAvailability(newAvailabilitySlot);
		appointment.setBookingTime(LocalDateTime.now());
		
		newAvailabilitySlot.setBooked(true);
		availRepository.save(newAvailabilitySlot);
		
		return appointmentRepository.save(appointment);
		
	}

	// delete
	public void deleteAppointment(Long appointmentId) throws BadRequestException {
		Appointments appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new BadRequestException("Appointment not found"));

		appointmentRepository.delete(appointment);
	}

	// get all
	@Override
	public List<Appointments> getAllAppointments() {
		// Fetch all appointments from the repository
		return appointmentRepository.findAll();
	}

	// get by user
	@Override
	public List<Appointments> getAppointmentsByUserId(Long userId) {
		return appointmentRepository.findByUserId(userId);
	}

	// get 1 hour before
	@Override
	public List<Appointments> getAppointmentsWithinNextHour(LocalDateTime currentTime) {
		// Fetch all appointments
		List<Appointments> appointments = appointmentRepository.findAll();

		// Filter appointments that are 1 hour away from now
		return appointments.stream()
				.filter(appointment -> ChronoUnit.MINUTES.between(currentTime, appointment.getStartTime()) == 60)
				.collect(Collectors.toList());
	}

	// isTeacherAvailable
	/*
	 * private Availability isTeacherAvailable(User teacher, LocalDateTime
	 * startTime, LocalDateTime endTime) {
	 * 
	 * 
	 * 
	 * List<Availability> availabilities = teacher.getAvailability();
	 * 
	 * if (availabilities == null || availabilities.isEmpty()) { return null; }
	 * 
	 * for (Availability availability : availabilities) { LocalDateTime
	 * availabilityStartTime = availability.getStartTime(); LocalDateTime
	 * availabilityEndTime = availability.getEndTime();
	 * 
	 * if (startTime.isAfter(availabilityStartTime) &&
	 * endTime.isBefore(availabilityEndTime)) { return availability; } }
	 * 
	 * return null; }
	 */
}
