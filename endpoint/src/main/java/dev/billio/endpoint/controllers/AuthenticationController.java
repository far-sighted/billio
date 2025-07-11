package dev.billio.endpoint.controllers;

import dev.billio.endpoint.models.UserModel;
import dev.billio.endpoint.dto.AuthenticationDto;
import dev.billio.endpoint.utils.JsonWebTokenizer;
import dev.billio.endpoint.services.implementation.UserService;
import dev.billio.endpoint.services.implementation.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "Authentication", description = "Endpoints for user authentication, including login and token management.")
public class AuthenticationController {

    //region Fields
    private final UserService userService;
    private final JsonWebTokenizer jsonWebTokenizer;
    private final AuthenticationService authenticationService;
    //endregion Fields

    /**
     * Constructs an AuthenticationController with the specified AuthenticationService and JsonWebTokenizer.
     *
     * @param authenticationService the service to handle authentication logic
     * @param jsonWebTokenizer      the utility for handling JSON Web Tokens
     * @param userService           the service to manage user data
     */
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JsonWebTokenizer jsonWebTokenizer, UserService userService) {
        this.authenticationService = authenticationService;
        this.jsonWebTokenizer = jsonWebTokenizer;
        this.userService = userService;
    }

    /**
     * Handles user login by authenticating the provided credentials.
     *
     * @param user the authentication request containing username and password
     * @return the authenticated UserModel
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto.JwtResponseDTO> authenticate(@RequestBody AuthenticationDto.AuthenticationRequest user) {
        UserModel authenticatedUser = authenticationService.authenticate(user);

        AuthenticationDto.JwtResponseDTO responseDTO = new AuthenticationDto.JwtResponseDTO(
                new AuthenticationDto.JwtResponseDTO.Token(jsonWebTokenizer.generateToken(authenticatedUser), jsonWebTokenizer.getExpirationTime()),
                new AuthenticationDto.JwtResponseDTO.RefreshToken(jsonWebTokenizer.generateRefreshToken(authenticatedUser), jsonWebTokenizer.getRefreshExpirationTime())
        );

        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    /**
     * Handles user registration by creating a new user account.
     *
     * @param user the UserModel containing user details for registration
     * @return the registered UserModel
     */
    @PostMapping("/register")
    public UserModel Register(@RequestBody UserModel user) {
        return authenticationService.register(user);
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return the UserModel of the authenticated user
     */
    @GetMapping("/user")
    public ResponseEntity<UserModel> GetMe() {
        try {
            // Get the current user from the security context
            UserModel currentUser = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new ResponseEntity<>(currentUser, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Refreshes the authentication token for the user.
     *
     * @param body a map containing the refresh token
     * @return a new access token if the refresh token is valid, or an unauthorized response
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationDto.JwtResponseDTO.Token> refresh(@RequestBody Map<String, String> body) {
        // Check if the refresh token is provided in the request body
        if (body.get("refreshToken") == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validate the refresh token and generate a new access token
        try {
            String username = jsonWebTokenizer.extractUsername(body.get("refreshToken"));
            UserModel user = userService.find(username); // or userDetailsService.loadUserByUsername

            // Check if the user exists and if the refresh token is valid
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Validate the refresh token
            if (!jsonWebTokenizer.isTokenValid(body.get("refreshToken"), user)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Generate a new access token
            String newAccessToken = jsonWebTokenizer.generateToken(user);
            AuthenticationDto.JwtResponseDTO.Token token = new AuthenticationDto.JwtResponseDTO.Token(
                    newAccessToken,
                    jsonWebTokenizer.getExpirationTime()
            );

            // Optionally, you can also generate a new refresh token
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
