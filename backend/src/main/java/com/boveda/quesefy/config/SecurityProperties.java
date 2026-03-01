package com.boveda.quesefy.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "quesefy.security")
public class SecurityProperties {

    @Valid
    private final UserCredentials admin = new UserCredentials();

    @Valid
    private final UserCredentials user = new UserCredentials();

    public UserCredentials getAdmin() {
        return admin;
    }

    public UserCredentials getUser() {
        return user;
    }

    public static class UserCredentials {
        @NotBlank
        private String username;

        @NotBlank
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
