package com.example.Teacher_portal.controller;

import com.example.Teacher_portal.response.OAuthTokenResponse;
import com.example.Teacher_portal.service.impl.InMemoryTokenStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Teacher_portal.service.OAuth2Service;

import java.util.Map;


@RestController
@RequestMapping("/oauth2")
@Tag(name = "Zoom_OAuth2_Controller")
public class OAuth2Controller {

    @Autowired
    private OAuth2Service oAuth2Service;

    @Autowired
    private InMemoryTokenStore tokenStore;


    public OAuth2Controller(OAuth2Service oAuth2Service) {
        super();
        this.oAuth2Service = oAuth2Service;
    }


    @GetMapping("/authorize")
    public String authorize() {

        return "redirect:" + oAuth2Service.getAuthorizationUrl();
    }

    @PostMapping("/access_token")
    public ResponseEntity<OAuthTokenResponse> exchangeToken(@RequestBody Map<String, String> request) {

        String authorizationCode = request.get("code");

        if (authorizationCode == null || authorizationCode.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            ResponseEntity<OAuthTokenResponse> tokenResponse = oAuth2Service.exchangeAuthorizationCodeForToken(authorizationCode);
            return tokenResponse; // Return the full ResponseEntity from the service
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<OAuthTokenResponse> refreshAccessToken() {

//        String refreshToken = tokenStore.getRefreshToken();
//
//        if (refreshToken == null || refreshToken.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }

        try {
            ResponseEntity<OAuthTokenResponse> tokenResponse = oAuth2Service.refreshAccessToken();
            return tokenResponse;
        } catch (RuntimeException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
