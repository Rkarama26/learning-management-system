package com.example.Teacher_portal.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MeetingDetailsResponse {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("id")
    private long id;

    @JsonProperty("host_id")
    private String hostId;

    @JsonProperty("host_email")
    private String hostEmail;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("type")
    private int type;

    @JsonProperty("status")
    private String status;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("agenda")
    private String agenda;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("start_url")
    private String startUrl;

    @JsonProperty("join_url")
    private String joinUrl;

    @JsonProperty("password")
    private String password;

    @JsonProperty("settings")
    private Settings settings;


    // Nested Settings Class
    public static class Settings {

        @JsonProperty("host_video")
        private boolean hostVideo;

        @JsonProperty("participant_video")
        private boolean participantVideo;

        @JsonProperty("join_before_host")
        private boolean joinBeforeHost;

        @JsonProperty("mute_upon_entry")
        private boolean muteUponEntry;

        @JsonProperty("waiting_room")
        private boolean waitingRoom;

        @JsonProperty("approval_type")
        private int approvalType;


    }
}
