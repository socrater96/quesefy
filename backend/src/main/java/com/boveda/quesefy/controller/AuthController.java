package com.boveda.quesefy.controller;

import com.boveda.quesefy.domain.dto.AuthenticatedUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/auth")
@SecurityRequirement(name = "basicAuth")
public class AuthController {

    @Operation(
            summary = "Return the currently authenticated user",
            description = "Validates the provided credentials and returns the authenticated principal details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Credentials are valid"),
            @ApiResponse(responseCode = "401", description = "Credentials are missing or invalid")
    })
    @GetMapping("/me")
    public ResponseEntity<AuthenticatedUserDto> getCurrentUser(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring("ROLE_".length()))
                .sorted()
                .toList();

        return ResponseEntity.ok(new AuthenticatedUserDto(authentication.getName(), roles));
    }
}
