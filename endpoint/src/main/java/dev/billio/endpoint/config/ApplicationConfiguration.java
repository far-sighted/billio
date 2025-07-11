package dev.billio.endpoint.config;

import dev.billio.endpoint.repositories.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class ApplicationConfiguration {

    //region Fields
    private final UserRepository userRepository;
    //endregion Fields

    /**
     * Constructs an ApplicationConfiguration with the specified UserRepository.
     *
     * @param userRepository the UserRepository to be used by this configuration
     */
    @Autowired
    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Provides a UserDetailsService bean that retrieves user details by username.
     *
     * @return a UserDetailsService that loads user details from the UserRepository
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Provides a PasswordEncoder bean that uses BCrypt for password encoding.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an AuthenticationManager bean that is configured for authentication.
     *
     * @param config the AuthenticationConfiguration to be used for creating the AuthenticationManager
     * @return an AuthenticationManager instance
     * @throws Exception if there is an error creating the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides an AuthenticationProvider bean that uses the UserDetailsService and PasswordEncoder.
     *
     * @return an AuthenticationProvider configured with the UserDetailsService and PasswordEncoder
     */
    @Bean
    @SuppressWarnings("deprecation")
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
