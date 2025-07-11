package dev.billio.endpoint.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Billio API",
                version = "1.0.0",
                description = "API for Billio, a platform for managing and tracking bills."
        ),
        servers = {
                @io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8080", description = "Local server")
        },
        security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearer")
)
@Configuration
public class OpenAPIConfiguration {
}
