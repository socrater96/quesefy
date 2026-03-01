package com.boveda.quesefy.config;

import com.boveda.quesefy.domain.dto.ErrorDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {
    private final ObjectMapper objectMapper;

    public SecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
                        .requestMatchers("/api/v1/auth/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/events/**", "/api/v1/venues/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/v1/events/**", "/api/v1/venues/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, exception) ->
                                writeError(response, HttpStatus.UNAUTHORIZED, "Authentication required"))
                        .accessDeniedHandler((request, response, exception) ->
                                writeError(response, HttpStatus.FORBIDDEN, "Access denied"))
                )
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(
            SecurityProperties securityProperties,
            PasswordEncoder passwordEncoder
    ) {
        UserDetails adminUser = buildUser(
                securityProperties.getAdmin(),
                "ADMIN",
                passwordEncoder
        );

        UserDetails standardUser = buildUser(
                securityProperties.getUser(),
                "USER",
                passwordEncoder
        );

        return new InMemoryUserDetailsManager(adminUser, standardUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetails buildUser(
            SecurityProperties.UserCredentials credentials,
            String role,
            PasswordEncoder passwordEncoder
    ) {
        return User.withUsername(credentials.getUsername())
                .password(passwordEncoder.encode(credentials.getPassword()))
                .roles(role)
                .build();
    }

    private void writeError(
            jakarta.servlet.http.HttpServletResponse response,
            HttpStatus status,
            String message
    ) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), new ErrorDto(message));
    }
}
