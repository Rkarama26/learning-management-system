package com.example.Teacher_portal.service;

import com.example.Teacher_portal.Entity.Appointments;

public interface NotificationService {
	
	void sendAppointmentReminder(Appointments appointment);
	}


