package com.base.recruitment.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    String generateToken(UserDetails user);
    String extractUsername(String token);
    boolean validateToken(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
}
