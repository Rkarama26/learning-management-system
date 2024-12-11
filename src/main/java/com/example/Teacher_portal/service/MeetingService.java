package com.example.Teacher_portal.service;

import com.example.Teacher_portal.request.CreateMeetingReq;
import com.example.Teacher_portal.response.CreateMeetingRes;
import org.springframework.http.ResponseEntity;

public interface MeetingService {

  //  public ResponseEntity<ClUserResponse> getUserInfo(OAuth2AuthenticationToken authenticationToken);

    public ResponseEntity<CreateMeetingRes> createMeeting(CreateMeetingReq request);
}
