package dev.billio.endpoint.services.interfaces;

import dev.billio.endpoint.dto.AuthenticationDto;
import dev.billio.endpoint.models.UserModel;

public interface AuthenticationInterface {

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param authenticationRequest the request containing user credentials
     * @return an AuthenticationResponse containing authentication details
     */
    UserModel authenticate(AuthenticationDto.AuthenticationRequest authenticationRequest);

    /**
     * Registers a new user in the system.
     *
     * @param user the UserModel containing user details for registration
     * @return the registered UserModel
     */
    UserModel register(UserModel user);

}
