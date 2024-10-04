package com.example.Teacher_portal.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.service.reminder.AppointmetScheduler;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AppointmentSchedulerTest {

	@Mock
	private AppointmentService appointmentService;

	@Mock
	private NotificationService notificationService;

	@InjectMocks
	private AppointmetScheduler appointmentScheduler;

	@Test
	public void testSendAppointmentReminders() {

		// Arrange
		LocalDateTime currentTime = LocalDateTime.now();
		Appointments appointment = mock(Appointments.class);

		List<Appointments> appointments = Arrays.asList(appointment);

		when(appointmentService.getAppointmentsWithinNextHour(any(LocalDateTime.class))).thenReturn(appointments);
		
		// Act
		appointmentScheduler.sendAppointmentReminders();

		// Assert
		verify(notificationService, times(1)).sendAppointmentReminder(appointment); // Verify method call
	}

}
