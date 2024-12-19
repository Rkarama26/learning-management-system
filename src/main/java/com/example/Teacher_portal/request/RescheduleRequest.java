package com.example.Teacher_portal.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RescheduleRequest {

    private LocalDateTime newStartTime;
    private LocalDateTime newEndTime;

}

