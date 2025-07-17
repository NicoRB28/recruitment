package com.base.recruitment.dto.error;

public record ApiError(
        String mensaje,
        long codigo
) {
}
