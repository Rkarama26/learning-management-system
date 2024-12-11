package com.example.Teacher_portal.service.impl;

import com.example.Teacher_portal.response.OAuthTokenResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class InMemoryTokenStore {


    private OAuthTokenResponse tokenResponse; // Store the entire token response
    private Instant tokenExpiryTime; // Tracks when the access token expires

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

}
