package com.example.Teacher_portal.request;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RescheduleRequest {

	private LocalDateTime newStartTime;
    private LocalDateTime newEndTime;
    private DayOfWeek dayOfWeek;
    
}

