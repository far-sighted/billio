package dev.billio.endpoint.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

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
@SecurityScheme(
        scheme = "bearer",
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        description = "JWT authentication using Bearer token",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenAPIConfiguration {
}
