package dev.billio.endpoint.repositories;

import dev.billio.endpoint.models.UserModel;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return an Optional containing the UserModel if found, or empty if not found
     */
    Optional<UserModel> findByUsername(String username);
}
