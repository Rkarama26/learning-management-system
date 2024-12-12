package com.example.Teacher_portal.service.impl;

import com.example.Teacher_portal.response.OAuthTokenResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class InMemoryTokenStore {


    private OAuthTokenResponse tokenResponse; // Store the entire token response
    private Instant tokenExpiryTime; // Tracks when the access token expires
    private static final int NEXT_PAGE_TOKEN_EXPIRATION_MINUTES = 15;
    private String nextPageToken;
    private LocalDateTime nextPageTokenTimestamp;


    // Save the token response and calculate expiry time
    public void saveToken(OAuthTokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
        if (tokenResponse.getExpiresIn() > 0) {
            // Calculate and save token expiry time based on expiresIn field (seconds)
            this.tokenExpiryTime = Instant.now().plusSeconds(tokenResponse.getExpiresIn());
        }

        System.out.println("Saving token: " + tokenResponse.getAccessToken());
    }

    // Retrieve the current token response
    public OAuthTokenResponse getOAuthTokenresponse() {
        return tokenResponse;
    }

    // Check if the token is valid (not expired)
    public boolean hasValidToken() {
        return tokenResponse != null && Instant.now().isBefore(tokenExpiryTime);
    }

    // Retrieve the refresh token for refreshing access token
    public String getRefreshToken() {
        return tokenResponse != null ? tokenResponse.getRefreshToken() : null;
    }

    // Retrieve the access token
    public String getAccessToken() {
        return tokenResponse != null ? tokenResponse.getAccessToken() : null;
    }

    // Retrieve the scope associated with the token
    public String getScope() {
        return tokenResponse != null ? tokenResponse.getScope() : null;
    }

    // Clear stored token (in case of logout or invalidation)
    public void clearToken() {
        tokenResponse = null;
        tokenExpiryTime = null;
    }
    public String getNextPageToken() {
        if (nextPageToken != null && isTokenExpired()) {
            // Token expired, return null to force a refresh
            return null;
        }
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
        this.nextPageTokenTimestamp = LocalDateTime.now();
    }

    private boolean isTokenExpired() {
        if (nextPageTokenTimestamp == null) {
            return true; // No token set yet
        }
        long minutesElapsed = ChronoUnit.MINUTES.between(nextPageTokenTimestamp, LocalDateTime.now());
        return minutesElapsed > NEXT_PAGE_TOKEN_EXPIRATION_MINUTES;
    }


}
