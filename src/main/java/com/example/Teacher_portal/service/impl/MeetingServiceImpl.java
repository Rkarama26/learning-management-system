package com.example.Teacher_portal.service.impl;

import com.example.Teacher_portal.request.CreateMeetingReq;
import com.example.Teacher_portal.response.CreateMeetingRes;
import com.example.Teacher_portal.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InMemoryTokenStore tokenStore;



    @Override
    public ResponseEntity<CreateMeetingRes> createMeeting(CreateMeetingReq request) {

        String url = "https://api.zoom.us/v2/users/me/meetings";

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tokenStore.getAccessToken());
        System.out.println("accToken: "+ tokenStore.getAccessToken());

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

