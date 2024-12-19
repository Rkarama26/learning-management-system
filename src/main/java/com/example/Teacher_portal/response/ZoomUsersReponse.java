package com.example.Teacher_portal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class ZoomUsersReponse {

    @JsonProperty("page_count")
    private int pageCount;

    @JsonProperty("page_number")
    private int pageNumber;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("total_records")
    private int totalRecords;

    @JsonProperty("next_page_token")
    private String nextPageToken;

    private ArrayList<User> users;


    public class User {

        private String id;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        @JsonProperty("display_name")
        private String displayName;

        private String email;

        private int type;

        private long pmi;

        private String timezone;

        private int verified;

        @JsonProperty("created_at")
        private Date createdAt;

        @JsonProperty("last_login_time")
        private Date lastLoginTime;

        @JsonProperty("pic_url")
        private String picUrl;

        @JsonProperty("phone_number")
        private String phoneNumber;

        private String status;
    }

}
