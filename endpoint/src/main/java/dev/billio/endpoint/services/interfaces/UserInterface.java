package dev.billio.endpoint.services.interfaces;

import dev.billio.endpoint.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserInterface {

    /**
     * Retrieves a paginated list of users.
     *
     * @param pageable the pagination information
     * @return a Page containing UserModel objects
     */
    Page<UserModel> get(Pageable pageable);

    /**
     * Finds a user by UUID.
     *
     * @param uuid the UUID of the user
     * @return the UserModel object corresponding to the given UUID
     */
    UserModel find(UUID uuid);

    /**
     * Saves a new user or updates an existing user.
     *
     * @param user the UserModel object to be saved or updated
     * @return the saved UserModel object
     */
    UserModel save(UserModel user);

    /**
     * Updates an existing user.
     *
     * @param user the UserModel object with updated information
     * @return the updated UserModel object
     */
    UserModel update(UserModel user);

    /**
     * Deletes a user by UUID.
     *
     * @param uuid the UUID of the user to be deleted
     */
    void delete(UUID uuid);
}
