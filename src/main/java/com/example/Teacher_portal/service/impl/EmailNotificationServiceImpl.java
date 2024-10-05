package com.example.Teacher_portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.service.NotificationService;
import com.example.Teacher_portal.service.reminder.EmailService;

@Service
public class EmailNotificationServiceImpl implements NotificationService {

	@Autowired
	private EmailService emailService;

	@Override
	public void sendAppointmentReminder(Appointments appointment) {

		User teacher = appointment.getTeacher();
		User student = appointment.getStudent();
		String teacherEmail = teacher.getEmail();
		String studentEmail = student.getEmail();
		String subject = "Reminder: Upcoming Appointment";

		String body = "This is a reminder that you have an upcoming appointment scheduled on "
				 + " at " + appointment.getStartTime();

		emailService.sendEmail(teacherEmail, subject, body);
		emailService.sendEmail(studentEmail, subject, body);

		System.out.println("Email sent successfully to both teacher and student.");

	}

}
