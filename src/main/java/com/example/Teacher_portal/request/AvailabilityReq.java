package com.example.Teacher_portal.request;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AvailabilityReq {

	private DayOfWeek dayOfWeek;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

}
