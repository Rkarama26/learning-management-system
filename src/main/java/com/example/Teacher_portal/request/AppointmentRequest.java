package com.example.Teacher_portal.request;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentRequest {

    private Long teacherId;
	private LocalDateTime startTime;
    private LocalDateTime endTime;
    
}