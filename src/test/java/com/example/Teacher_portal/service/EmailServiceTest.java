package com.example.Teacher_portal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.service.reminder.EmailService;
import org.mockito.MockitoAnnotations;

@SpringBootTest
public class EmailServiceTest {

	@Mock
	private JavaMailSender mailSender;

	@InjectMocks
	private EmailService emailService;

	private User testTeacher;

	private Appointments testAppointment;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		testTeacher = new User();
		testTeacher.setEmail("rv262003@gmail.com");
		testTeacher.setFirstName("Rohit");

		testAppointment = new Appointments();
		testAppointment.setStartTime(LocalDateTime.of(2024, 1, 1, 11, 0)); // 10:00 AM
		testAppointment.setEndTime(LocalDateTime.of(2024, 1, 1, 11, 0));

	}
	
	

/*	@Test
	public void testSendAppointmentReminder() {
		String response = emailService.sendAppointmentReminder(testTeacher, testAppointment);
		assertEquals("Email sent successfully", response); // Ensure the response matches
	}
	*/
	
	@Test
	void testSendMail() {
		emailService.sendEmail("rv262003@gmail.com", 
				              "testing java mail sender", 
				"Hii how are you");
	}
	
	
}
