package com.example.Teacher_portal.service.impl;

import com.example.Teacher_portal.response.OAuthTokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.Teacher_portal.constants.CLconstants;
import com.example.Teacher_portal.service.OAuth2Service;

import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class OAuth2ServiceImpl implements OAuth2Service {

	
/*
@Value("${security.oauth2.client.registration.zoom.client-id}")
	private String clientId;
	
    @Value("${security.oauth2.client.registration.zoom.client-secret}")
	private String clientSecret;
	
    @Value("${security.oauth2.client.registration.zoom.redirect-uri}")
	private String redirectUri;
	
    @Value("${security.oauth2.client.provider.zoom.authorization-uri}")
	private String basicZoomAuthUri;
	
    @Value("${security.oauth2.client.provider.zoom.token-uri}")
	private String tokenUri;

*/

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private OAuthTokenResponse oAuthTokenResponse = new OAuthTokenResponse();


    //Get authorize
    public String getAuthorizationUrl() {
        // Zoom OAuth API URL
        String zoomOAuthUrl = UriComponentsBuilder.fromHttpUrl(CLconstants.AUTHORIZATION_URL).queryParam("response_type", "code").queryParam("client_id", CLconstants.CLIENT_ID).queryParam("redirect_uri", CLconstants.REDIRECT_URI).toUriString();


        // GET request using exchange()
        ResponseEntity<String> response = restTemplate.exchange(zoomOAuthUrl, HttpMethod.GET, null, String.class);

        return response.getBody();
    }

    // Get access token
    @Override
    public ResponseEntity<OAuthTokenResponse> exchangeAuthorizationCodeForToken(String authorizationCode) {

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(CLconstants.CLIENT_ID, CLconstants.CLIENT_SECRET); // Basic Auth

        // Build the request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", authorizationCode);
        body.add("redirect_uri", CLconstants.REDIRECT_URI);

        // Create HttpEntity with headers and body
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Make the POST request and map response to ResponseEntity<OAuthTokenResponse>
            ResponseEntity<OAuthTokenResponse> response = restTemplate.exchange(
                    CLconstants.TOKEN_ENDPOINT,
                    HttpMethod.POST,
                    requestEntity,
                    OAuthTokenResponse.class
            );

            // Return the full ResponseEntity, including headers, body, and status
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error while exchanging authorization code for token", e);
        }
    }

    //Refresh access token
    @Override
    public ResponseEntity<OAuthTokenResponse> refreshAccessToken(String refreshToken) throws JsonProcessingException {

        String url = CLconstants.TOKEN_ENDPOINT;

        String requestBody = "grant_type=refresh_token&refresh_token=" + refreshToken + "&client_id=" + CLconstants.CLIENT_ID + "&client_secret=" + CLconstants.CLIENT_SECRET;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);


        ResponseEntity<OAuthTokenResponse> response = restTemplate
                .exchange(url,
                        HttpMethod.POST,
                        requestEntity,
                        OAuthTokenResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {


            OAuthTokenResponse tokenResponse = response.getBody();
            if (tokenResponse != null) {

                //System.out.println("Access Token: " + tokenResponse.getAccessToken());
                //System.out.println("Refresh Token: " + tokenResponse.getRefreshToken());

                return response;
            } else {
                throw new RuntimeException("Failed to refresh token: response body null");
            }
        } else {
            throw new RuntimeException("External Api Error: " + response.getStatusCode());
        }

    }

}
