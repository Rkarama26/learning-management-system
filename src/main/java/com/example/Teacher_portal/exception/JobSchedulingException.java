package com.example.Teacher_portal.exception;

import org.quartz.SchedulerException;

public class JobSchedulingException extends Exception {

	public JobSchedulingException(String message, SchedulerException e) {
		super(message);
	}
	
	

}
