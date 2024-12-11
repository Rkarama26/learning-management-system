package com.example.Teacher_portal.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentReq {

    private Long teacherId;
	private LocalDateTime startTime;
    private LocalDateTime endTime;
    
}