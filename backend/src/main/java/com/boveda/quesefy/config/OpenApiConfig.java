package com.boveda.quesefy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Quesefy",
                description = "REST API for discovering a managing events",
                version = "1.0.0"
        )
)
public class OpenApiConfig {
}
