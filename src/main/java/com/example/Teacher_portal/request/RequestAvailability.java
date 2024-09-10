package com.example.Teacher_portal.request;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestAvailability {
	
	 private String dayOfWeek;
	    
	 private LocalTime startTime;
	    
	 private LocalTime endTime;

}
