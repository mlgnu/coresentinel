package com.districtcore.coresentinel.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EvidenceDeletionConfirmationService {

    // To improve: Implement a more robust token management system using Redis or a database
    ConcurrentHashMap<String, TokenDetails> deleteTokens = new ConcurrentHashMap<>();

    public String createDeleteToken(Long id) {
        cleanTokens();
        String token = UUID.randomUUID().toString();
        LocalDateTime tokenExpiry = LocalDateTime.now().plusMinutes(5);

        deleteTokens.put(token,
                new TokenDetails(id, tokenExpiry));
        return token;
    }

    public boolean validateDeleteToken(String token) {
        LocalDateTime expiryTime = deleteTokens.get(token).getExpiryTime();
        if (expiryTime != null && LocalDateTime.now().isBefore(expiryTime)) {
            deleteTokens.remove(token);
            return true;
        }
        return false;
    }

    public Long getEvidenceIdFromToken(String token) {
        TokenDetails tokenDetails = deleteTokens.get(token);
        if (tokenDetails != null) {
            return tokenDetails.getId();
        }
        return null;
    }

    public void cleanTokens() {
        LocalDateTime now = LocalDateTime.now();
        deleteTokens.entrySet().removeIf(entry -> entry.getValue().getExpiryTime().isBefore(now));
    }

    @Setter
    @Getter
    @AllArgsConstructor
    static class TokenDetails {
        private Long id;
        private LocalDateTime expiryTime;
    }
}

