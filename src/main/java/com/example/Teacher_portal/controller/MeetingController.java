package com.example.Teacher_portal.controller;

import com.example.Teacher_portal.request.CreateMeetingReq;
import com.example.Teacher_portal.response.CreateMeetingRes;
import com.example.Teacher_portal.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

//    @GetMapping("/user")
//    public ResponseEntity<ClUserResponse> getZoomUserInfo(OAuth2AuthenticationToken authenticationToken) {
//        ResponseEntity<ClUserResponse> response = classRoomService.getUserInfo(authenticationToken);
//
//        return response;
//    }

    @PostMapping("/create")
    public ResponseEntity<CreateMeetingRes> createMeeting(@RequestHeader("Authorization") String jwt,
                                                          @RequestBody CreateMeetingReq request) {
        // Validate Authorization Header
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new CreateMeetingRes("Invalid or missing Authorization header"));
//        }

        // Extract Access Token
      //  System.out.println("Token: " + Token);
      //  String accessToken = Token.substring(7);

        try {
            CreateMeetingRes response = meetingService.createMeeting(request).getBody();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateMeetingRes());
        }
    }


}
