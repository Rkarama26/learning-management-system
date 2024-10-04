package com.example.Teacher_portal.service.reminder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.repository.AppointmentRepository;
import com.example.Teacher_portal.service.AppointmentService;
import com.example.Teacher_portal.service.NotificationService;

@Component
public class AppointmetScheduler {

	   @Autowired
	    private AppointmentService appointmentService;

	    @Autowired
	    private NotificationService notificationService;

	@Scheduled(fixedRate = 60000) // Runs every minute
    public void sendAppointmentReminders() {
		
		LocalDateTime currentTime = LocalDateTime.now();
		
		List<Appointments> upComingAppointments = appointmentService.getAppointmentsWithinNextHour(currentTime);
				{
			for(Appointments appointment : upComingAppointments) {
				notificationService.sendAppointmentReminder(appointment);
			}
		}
	}

}
