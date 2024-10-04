package com.example.Teacher_portal.service.impl;

import java.time.DayOfWeek;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.Entity.Availability;
import com.example.Teacher_portal.Entity.Status;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.NoTeacherFoundException;
import com.example.Teacher_portal.exception.SlotAlreadyBookedException;
import com.example.Teacher_portal.repository.AppointmentRepository;
import com.example.Teacher_portal.repository.AvailRepository;
import com.example.Teacher_portal.repository.UserRepository;
import com.example.Teacher_portal.request.AppointmentRequest;
import com.example.Teacher_portal.request.RescheduleRequest;
import com.example.Teacher_portal.service.AppointmentService;
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

	// create
	public Appointments createAppointment(AppointmentRequest request) throws BadRequestException {

		User student = userRepository.findById(request.getStudentId()).orElseThrow();
		User teacher = userRepository.findById(request.getTeacherId()).orElseThrow();

		// Retrieve teacher availability
		boolean isTeacherAvailable = isTeacherAvailable(teacher, request.getDayOfWeek(), request.getStartTime(),
				request.getEndTime());
		if (!isTeacherAvailable) {
			throw new NoTeacherFoundException("No teacher available during the specified time range.");
		}
		Appointments existingAppointment = appointmentRepository.findByTeacherAndDayOfWeekAndStartTimeAndEndTime(
				teacher, request.getDayOfWeek(), request.getStartTime(), request.getEndTime());
		if (existingAppointment != null) {
			throw new SlotAlreadyBookedException("Slot already booked for teacher " + student.getFirstName() + " on "
					+ request.getDayOfWeek() + " at " + request.getStartTime());
		}

		// Create new appointment
		Appointments appointment = new Appointments();
		appointment.setStudent(student);
		appointment.setTeacher(teacher);
		appointment.setStartTime(request.getStartTime());
		appointment.setEndTime(request.getEndTime());
		appointment.setDayOfWeek(request.getDayOfWeek());
		appointment.setStatus(Status.PENDING);

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

	// isTeacherAvailable
	private boolean isTeacherAvailable(User teacher, DayOfWeek dayOfWeek, LocalDateTime startTime,
			LocalDateTime endTime) {

		List<Availability> availabilities = teacher.getAvailability();

		// the teacher has any availability at all
		if (availabilities == null || availabilities.isEmpty()) {
			return false;
		}

		// specified day of week
		Availability availability = availabilities.stream().filter(a -> a.getDayOfWeek() == dayOfWeek).findFirst()
				.orElse(null);

		if (availability == null) {
			return false;
		}

		// specified time range
		LocalDateTime availabilityStartTime = availability.getStartTime();
		LocalDateTime availabilityEndTime = availability.getEndTime();

		if (!(startTime.isAfter(availabilityStartTime) && endTime.isBefore(availabilityEndTime))) {

			return false;
		}

		return true;
	}

	// confirm
	@Transactional
	public Appointments confirmAppointment(Long appointmentId) {

		Appointments appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

		appointment.setStatus(Status.CONFIRMED);

		User teacher = appointment.getTeacher();
		Availability availability = appointment.getAvailability();

		if (availability != null) {
			// Remove availability from teacher's profile
			List<Availability> teacherAvailabilities = teacher.getAvailability();
			for (Availability a : teacherAvailabilities) {
				if (a.getId().equals(availability.getId())) {
					teacherAvailabilities.remove(a);
					break;
				}
			}

			availRepository.deleteById(availability.getId());
		}
		// jobScheduler.scheduleJob(appointment);

		return appointmentRepository.save(appointment);

	}

	// decline
	public Appointments declineAppointment(Long appointmentId) throws BadRequestException {
		// deleteAppointment(appointmentId);
		Appointments appointment = appointmentRepository.findById(appointmentId).orElseThrow();
		appointment.setStatus(Status.CANCELLED);
		return appointmentRepository.save(appointment);
	}

	// reschedule
	public Appointments rescheduleAppointment(Long appointmentId, RescheduleRequest request) {
		Appointments appointment = appointmentRepository.findById(appointmentId).orElseThrow();

		if (isTeacherAvailable(appointment.getTeacher(), request.getDayOfWeek(), request.getNewStartTime(),
				request.getNewEndTime())) {

			appointment.setStartTime(request.getNewStartTime());
			appointment.setEndTime(request.getNewEndTime());
			appointment.setDayOfWeek(request.getDayOfWeek());
			return appointmentRepository.save(appointment);

		} else {

			throw new NoTeacherFoundException("Teacher is not available during the new time range");
		}
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

}
