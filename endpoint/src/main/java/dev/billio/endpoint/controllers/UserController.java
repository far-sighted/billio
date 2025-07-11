package dev.billio.endpoint.controllers;

import dev.billio.endpoint.enums.UserEnum;
import dev.billio.endpoint.models.UserModel;
import dev.billio.endpoint.services.implementation.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "User", description = "Endpoints for managing users in the Billio application. This includes operations for creating, retrieving, updating, and deleting user accounts.")
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
    public Page<UserModel> Get(Pageable pageable) {
        return userService.get(pageable);
    }

    /**
     * Handles HTTP GET requests to retrieve a user by UUID.
     *
     * @param uuid the UUID of the user
     * @return the UserModel object corresponding to the given UUID
     */
    @GetMapping("/{uuid}")
    public UserModel Get(@PathVariable("uuid") UUID uuid) {
        return userService.find(uuid);
    }

    /**
     * Handles HTTP POST and PUT requests to save or update a user.
     *
     * @param user the UserModel object to be saved or updated
     * @return the saved or updated UserModel object
     */
    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.PUT})
    public UserModel Save(@RequestBody UserModel user) {
        return userService.save(user);
    }

    /**
     * Handles HTTP DELETE requests to delete a user by UUID.
     *
     * @param uuid the UUID of the user to be deleted
     */
    @DeleteMapping("/{uuid}")
    public void Delete(@PathVariable("uuid") UUID uuid) {
        userService.delete(uuid);
    }

    /**
     * Handles HTTP POST requests to manage user permissions.
     *
     * @param uuid        the UUID of the user
     * @param permissions the set of permissions to be assigned to the user
     * @return the updated UserModel object with the new permissions
     */
    @PostMapping("/{uuid}/permissions")
    public UserModel handlePermissions(@PathVariable("uuid") UUID uuid, @RequestBody Set<UserEnum.eUserPermission> permissions) {
        return userService.handlePermissions(uuid, permissions);
    }

}
