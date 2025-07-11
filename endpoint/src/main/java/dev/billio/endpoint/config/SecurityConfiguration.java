package dev.billio.endpoint.config;

import dev.billio.endpoint.utils.JsonWebTokenFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    //region Fields
    private final AuthenticationProvider authenticationProvider;
    private final JsonWebTokenFilter jsonWebTokenFilter;
    //endregion Fields

    /**
     * Constructs a SecurityConfiguration with the specified AuthenticationProvider and JsonWebTokenFilter.
     *
     * @param authenticationProvider the AuthenticationProvider to be used for authentication
     * @param jsonWebTokenFilter     the JsonWebTokenFilter to be used for JWT validation
     */
    @Autowired
    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JsonWebTokenFilter jsonWebTokenFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jsonWebTokenFilter = jsonWebTokenFilter;
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // Disable CSRF protection as it is not needed for stateless APIs
                .authorizeHttpRequests(authorize -> authorize
                        // Allow unauthenticated access to specific endpoints
                        .requestMatchers("/api/auth/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        // Do not allow unauthenticated access to any other endpoints
                        .anyRequest().authenticated()
                )
                // Configure session management to be stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set the authentication provider
                .authenticationProvider(authenticationProvider)
                // Add the JWT filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jsonWebTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}