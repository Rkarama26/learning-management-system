package com.example.Teacher_portal.service;

import com.example.Teacher_portal.response.OAuthTokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface OAuth2Service {
	
    String getAuthorizationUrl();

    ResponseEntity<OAuthTokenResponse> exchangeAuthorizationCodeForToken(String authorizationCode);

    ResponseEntity<OAuthTokenResponse> refreshAccessToken(String refreshToken) throws JsonProcessingException;
}
