package com.example.Teacher_portal.service.reminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.Entity.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Async
	public void sendAppointmentReminder(User teacher, Appointments appointment) {
		String to = teacher.getEmail();
		String subject = "Appointment Request";
		String text = "Dear " + teacher.getFirstName() + ",\n\n"
				+ "You have a new appointment request from a student on "  + " from "
				+ appointment.getStartTime() + "hr to " + appointment.getEndTime() + "hr.\n"
				+ "Please check your appointments.\n\n" + "Best regards,\nAppointment System";

		sendEmail(to, subject, text);

	}

	@Async
	public void sendEmail(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			mailSender.send(message);
		} catch (MailException e) {

			e.printStackTrace();
			log.error("Exception while sending email", e);
		}
	}

}
