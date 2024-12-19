package com.example.Teacher_portal.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequest {

    private Long teacherId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}