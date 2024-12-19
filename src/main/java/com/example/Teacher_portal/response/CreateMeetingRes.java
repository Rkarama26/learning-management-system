package com.example.Teacher_portal.response;

import lombok.Data;

@Data
public class CreateMeetingRes {

    private String id;
    private String topic;
    private String start_time;
    private int duration;
    private String join_url;  // Participant link
    private String start_url; // Host link
}
