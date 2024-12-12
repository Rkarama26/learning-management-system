package com.example.Teacher_portal.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class ListMeetingsResponse {

    @JsonProperty("page_count")
    private int pageCount;

    @JsonProperty("page_number")
    private int pageNumber;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("total_records")
    private int totalRecords;

    @JsonProperty("meetings")
    private ArrayList<Meeting> meetings;

    @JsonProperty("next_page_token")
    private String nextPageToken;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public static class Meeting {

        @JsonProperty("uuid")
        private String uuid;

        @JsonProperty("id")
        private Object id;

        @JsonProperty("host_id")
        private String hostId;

        @JsonProperty("topic")
        private String topic;

        @JsonProperty("type")
        private int type;

        @JsonProperty("start_time")
        private Date startTime;

        @JsonProperty("duration")
        private int duration;

        @JsonProperty("timezone")
        private String timezone;

        @JsonProperty("created_at")
        private Date createdAt;

        @JsonProperty("join_url")
        private String joinUrl;

        @JsonProperty("support_go_live")
        private boolean supportGoLive;
    }
}
