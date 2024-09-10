package com.example.Teacher_portal.request;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ReqAvailability {

	private String dayOfWeek;

	private LocalTime startTime;

	private LocalTime endTime;

}
