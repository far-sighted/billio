package dev.billio.endpoint.services.implementation;

import dev.billio.endpoint.enums.UserEnum;
import dev.billio.endpoint.models.UserModel;
import dev.billio.endpoint.dto.AuthenticationDto;
import dev.billio.endpoint.services.interfaces.AuthenticationInterface;

import jakarta.persistence.PostPersist;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class AuthenticationService implements AuthenticationInterface {
    // This service will handle authentication logic, such as validating user credentials,
    // generating tokens, and managing user sessions. It will interact with the UserRepository
    // and possibly other services to perform these tasks.

    //region Fields
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    //endregion Fields

    /**
     * Constructs an AuthenticationService with the specified UserRepository, PasswordEncoder, and AuthenticationManager.
     *
     * @param userService           the UserRepository to be used by this service
     * @param authenticationManager the AuthenticationManager for managing authentication
     */
    @Autowired
    public AuthenticationService(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param authenticationRequest the request containing username and password
     * @return the authenticated UserModel
     */
    @Override
    public UserModel authenticate(AuthenticationDto.AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                ));

        return userService.find(authenticationRequest.getUsername());
    }

    /**
     * Registers a new user in the system.
     *
     * @param user the UserModel containing user details for registration
     * @return the registered UserModel
     */
    @Override
    @PostPersist
    public UserModel register(UserModel user) {
        user.getPermissions().add(UserEnum.eUserPermission.CAN_USER_STARTER);

        return userService.save(user);
    }
    
}
