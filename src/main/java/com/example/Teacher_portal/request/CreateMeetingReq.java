package com.example.Teacher_portal.request;

import lombok.Data;

@Data
public class CreateMeetingReq {


    private String topic;       // Meeting topic
    private int type;           // Meeting type (e.g., 2 for scheduled)
    private String start_time;  // Start time in ISO 8601 format
    private int duration;       // Duration in minutes
    private String timezone;    // Timezone (e.g., "America/New_York")


}
