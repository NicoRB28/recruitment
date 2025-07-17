package com.base.recruitment.dto.auth;

public record AuthRequest(
        String username,
        String password
) {
}
