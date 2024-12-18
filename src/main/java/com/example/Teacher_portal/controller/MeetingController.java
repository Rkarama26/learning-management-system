package com.example.Teacher_portal.controller;

import com.example.Teacher_portal.request.CreateMeetingReq;
import com.example.Teacher_portal.response.CreateMeetingRes;
import com.example.Teacher_portal.response.ListMeetingsResponse;
import com.example.Teacher_portal.response.MeetingDetailsResponse;
import com.example.Teacher_portal.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
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
        try {
            CreateMeetingRes response = meetingService.createMeeting(request).getBody();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateMeetingRes());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ListMeetingsResponse> getMeetings(@RequestHeader("Authorization") String jwt, @RequestParam(defaultValue = "30") int pageSize,
                                                            @RequestParam(defaultValue = "1") int pageNumber) {
        try {
            ListMeetingsResponse meetings = meetingService.listMeetings(pageSize, pageNumber);
            return ResponseEntity.ok(meetings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity<?> getMeetingDetails(@PathVariable String meetingId, @RequestHeader("Authorization") String jwt) {
        try {

            ResponseEntity<MeetingDetailsResponse> response = meetingService.getMeetingDetails(meetingId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
