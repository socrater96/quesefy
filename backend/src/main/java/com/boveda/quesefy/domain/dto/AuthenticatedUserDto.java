package com.boveda.quesefy.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record AuthenticatedUserDto(
        @Schema(
                description = "Authenticated username",
                example = "admin"
        )
        String username,

        @Schema(
                description = "Application roles assigned to the authenticated user",
                example = "[\"ADMIN\"]"
        )
        List<String> roles
) {
}
