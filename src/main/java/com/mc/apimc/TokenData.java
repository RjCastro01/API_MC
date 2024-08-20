package com.mc.apimc;

import java.time.Instant;

public class TokenData {
    private String accessToken;
    private String refreshToken;
    private long expiresAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isAccessTokenExpired() {
        return Instant.now().getEpochSecond() > expiresAt;
    }
}

