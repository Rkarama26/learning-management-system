package com.example.Teacher_portal.service.impl;

import com.example.Teacher_portal.request.CreateMeetingReq;
import com.example.Teacher_portal.response.CreateMeetingRes;
import com.example.Teacher_portal.response.ListMeetingsResponse;
import com.example.Teacher_portal.response.MeetingDetailsResponse;
import com.example.Teacher_portal.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InMemoryTokenStore tokenStore;

    private static final String ZOOM_API_URL = "https://api.zoom.us/v2/users/me/meetings";


    @Override
    public ResponseEntity<CreateMeetingRes> createMeeting(CreateMeetingReq request) {

        String url = "https://api.zoom.us/v2/users/me/meetings";

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tokenStore.getAccessToken());
        System.out.println("accToken: " + tokenStore.getAccessToken());

        // request body
        HttpEntity<CreateMeetingReq> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<CreateMeetingRes> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    CreateMeetingRes.class
            );

            if (response.getStatusCode() == HttpStatus.CREATED) {
                return response;
            } else {
                throw new RuntimeException("Failed to create meeting: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while calling zoom API: " + e.getMessage());
        }

    }

    @Override
    public ListMeetingsResponse listMeetings(int pageSize, int pageNumber) {
        String url = String.format(ZOOM_API_URL) +
                "?type=scheduled&page_size=" + pageSize + "&page_number=" + pageNumber;

        // If a next_page_token is available, add it to the URL
        String nextPageToken = tokenStore.getNextPageToken();
        if (nextPageToken != null) {
            url += "&next_page_token=" + nextPageToken;
        }

        // Headers with authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenStore.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ListMeetingsResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    ListMeetingsResponse.class
            );

            ListMeetingsResponse body = response.getBody();

            // Store next page token in memory for future requests
            if (body != null) {
                String newNextPageToken = body.getNextPageToken();
                tokenStore.setNextPageToken(newNextPageToken);
            }

            return body;
        } catch (Exception e) {
            throw new RuntimeException("Error while calling Zoom API: " + e.getMessage());
        }
    }

    @Override
    public MeetingDetailsResponse getMeetingDetails(String meetingId) {
        String url = "https://api.zoom.us/v2/meetings/" + meetingId;

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tokenStore.getAccessToken());

        // Make the API call
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<MeetingDetailsResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    MeetingDetailsResponse.class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching meeting details: " + e.getMessage(), e);
        }
    }

    @Override
    public String deleteMeeting(String meetingId) {
        String url = String.format("https://api.zoom.us/v2/meetings/%s?schedule_for_reminder=true&cancel_meeting_reminder=true", meetingId);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tokenStore.getAccessToken());

        // Prepare  request
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Make the DELETE API call
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                return "Meeting deleted successfully";
            } else {
                return "Meeting deletion failed with status: " + response.getStatusCode();
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error deleting the meeting: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while deleting the meeting: " + e.getMessage(), e);
        }
    }



//    @Override
//    public ResponseEntity<ClUserResponse> getUserInfo(OAuth2AuthenticationToken authenticationToken) {
//
//        //Load the authorized client
//        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
//                authenticationToken.getAuthorizedClientRegistrationId(),
//                authenticationToken.getName()
//        );
//
//        // Get access Token
//        String accessToken = authorizedClient.getAccessToken().getTokenValue();
//        System.out.println(accessToken);
//
//        // API url
//        String url = "https://api.zoom.us/v2/users/me";
//
//        //Authorization Headers with Access Token
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth("the access token is: "+accessToken);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<ClUserResponse> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                entity,
//                ClUserResponse.class
//        );
//        if (response.getStatusCode() == HttpStatus.OK) {
//
//            return response;
//        } else {
//            throw new RuntimeException("Failed to fetch user data: " + response.getStatusCode());
//        }
//     }


}

