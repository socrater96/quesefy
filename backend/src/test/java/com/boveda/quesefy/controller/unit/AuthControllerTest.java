package com.boveda.quesefy.controller.unit;

import com.boveda.quesefy.config.SecurityConfig;
import com.boveda.quesefy.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
@ImportAutoConfiguration({
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        ServletWebSecurityAutoConfiguration.class
})
@TestPropertySource(properties = {
        "quesefy.security.admin.username=admin",
        "quesefy.security.admin.password=admin123",
        "quesefy.security.user.username=user",
        "quesefy.security.user.password=user123"
})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn401WhenCredentialsAreMissing() throws Exception {
        mockMvc.perform(get("/api/v1/auth/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Authentication required"));
    }

    @Test
    void shouldReturnAuthenticatedAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/auth/me")
                .with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.roles[0]").value("ADMIN"));
    }

    @Test
    void shouldReturnAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/api/v1/auth/me")
                .with(httpBasic("user", "user123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.roles[0]").value("USER"));
    }
}
