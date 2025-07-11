package dev.billio.endpoint.services.implementation;

import dev.billio.endpoint.enums.UserEnum;
import dev.billio.endpoint.models.UserModel;
import dev.billio.endpoint.repositories.UserRepository;
import dev.billio.endpoint.services.interfaces.UserInterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserInterface {

    //region Fields
    private final UserRepository userRepository;
    //endregion Fields

    /**
     * Constructs a UserService with the specified UserRepository.
     *
     * @param userRepository the UserRepository to be used by this service
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a paginated list of users.
     *
     * @param pageable the pagination information
     * @return a Page containing UserModel objects
     */
    @Override
    public Page<UserModel> get(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Finds a user by UUID.
     *
     * @param uuid the UUID of the user
     * @return the UserModel object corresponding to the given UUID
     */
    @Override
    public UserModel find(UUID uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("User not found with UUID: " + uuid));
    }

    /**
     * Saves a new user or updates an existing user.
     *
     * @param user the UserModel object to be saved or updated
     * @return the saved UserModel object
     */
    @Override
    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    /**
     * Deletes a user by UUID.
     *
     * @param uuid the UUID of the user to be deleted
     */
    @Override
    public void delete(UUID uuid) {
        if (!userRepository.existsById(uuid)) {
            throw new RuntimeException("User not found with UUID: " + uuid);
        }
        userRepository.deleteById(uuid);
    }

    /**
     * Handles permissions for a user identified by UUID.
     *
     * @param uuid        the UUID of the user
     * @param permissions the set of permissions to be assigned to the user
     * @return the updated UserModel object with the new permissions
     */
    @Override
    public UserModel handlePermissions(UUID uuid, Set<UserEnum.eUserPermission> permissions) {
        UserModel user = userRepository.findById(uuid).orElseThrow(() -> new RuntimeException("User not found with UUID: " + uuid));
        user.setPermissions(permissions);
        return userRepository.save(user);
    }
}
