package dev.billio.endpoint.controllers;

import dev.billio.endpoint.models.UserModel;
import dev.billio.endpoint.services.implementation.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
public class UserController {

    //region Fields
    private final UserService userService;
    //endregion Fields

    /**
     * Constructs a UserController with the specified UserService.
     *
     * @param userService the UserService to be used by this controller
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles HTTP GET requests to retrieve a paginated list of users.
     *
     * @param pageable the pagination information
     * @return a paginated list of UserModel objects
     */
    @GetMapping
    public Page<UserModel> GET(Pageable pageable) {
        return userService.get(pageable);
    }

    /**
     * Handles HTTP GET requests to retrieve a user by UUID.
     *
     * @param uuid the UUID of the user
     * @return the UserModel object corresponding to the given UUID
     */
    @GetMapping("/{uuid}")
    public UserModel GET(@PathVariable("uuid") UUID uuid) {
        return userService.find(uuid);
    }

    /**
     * Handles HTTP POST requests to create a new user.
     *
     * @param user the UserModel object to be created
     * @return the created UserModel object
     */
    @PostMapping
    public UserModel POST(@RequestBody UserModel user) {
        return userService.save(user);
    }

    /**
     * Handles HTTP POST requests to update an existing user.
     *
     * @param user the UserModel object with updated information
     * @return the updated UserModel object
     */
    @PostMapping
    public UserModel UPDATE(@RequestBody UserModel user) {
        return userService.update(user);
    }

    /**
     * Handles HTTP DELETE requests to delete a user by UUID.
     *
     * @param uuid the UUID of the user to be deleted
     */
    @DeleteMapping("/{uuid}")
    public void DELETE(@PathVariable("uuid") UUID uuid) {
        userService.delete(uuid);
    }

}
