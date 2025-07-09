package dev.billio.endpoint.services;

import dev.billio.endpoint.enums.UserEnum;
import dev.billio.endpoint.models.UserModel;
import dev.billio.endpoint.repositories.UserRepository;
import dev.billio.endpoint.services.implementation.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.mockito.Mock;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    //region Mock Annotations
    @Mock
    private UserRepository userRepository;
    //endregion Mock Annotations

    //region InjectMocks Annotation
    @InjectMocks
    private UserService userService;
    //endregion InjectMocks Annotation

    //region Fields
    private UUID uuid;
    private UserModel user;
    //endregion Fields

    /**
     * This method is executed before each test case to set up the initial state.
     * It creates a new UserModel instance with a random UUID and predefined values.
     */
    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        user = new UserModel(uuid, "test_user", "user@test.com", "password123", UserEnum.eUserType.COMPANY);
    }

    /**
     * This test verifies that the get method of UserService returns a paginated list of users.
     * It mocks the userRepository to return a Page containing a single user and checks that the
     * returned Page has the expected number of elements.
     */
    @Test
    public void TEST_GET_ShouldReturn_PageOfUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserModel> page = new PageImpl<>(Collections.singletonList(user));

        when(userRepository.findAll(pageable)).thenReturn(page);

        Page<UserModel> result = userService.get(pageable);

        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findAll(pageable);
    }

    /**
     * This test verifies that the find method of UserService returns a user by UUID.
     * It mocks the userRepository to return an Optional containing the user and checks that
     * the returned UserModel has the expected username.
     */
    @Test
    public void TEST_FIND_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.find(uuid));
        assertEquals("User not found with UUID: " + uuid, exception.getMessage());
    }

    /**
     * This test verifies that the find method of UserService returns a user by UUID.
     * It mocks the userRepository to return an Optional containing the user and checks that
     * the returned UserModel has the expected username.
     */
    @Test
    public void TEST_SAVE_ShouldReturnSavedUser() {
        when(userRepository.save(user)).thenReturn(user);

        UserModel saved = userService.save(user);

        assertEquals("test_user", saved.getUsername());
        verify(userRepository).save(user);
    }

    /**
     * This test verifies that the update method of UserService updates a user
     * and returns the updated user.
     * It mocks the userRepository to return the updated user.
     */
    @Test
    public void TEST_UPDATE_ShouldReturnUpdatedUser() {
        when(userRepository.save(user)).thenReturn(user);

        UserModel updated = userService.update(user);

        assertEquals("test_user", updated.getUsername());
        verify(userRepository).save(user);
    }

    /**
     * This test verifies that the delete method of UserService deletes a user
     * when the user exists in the repository.
     */
    @Test
    public void TEST_DELETE_ShouldDeleteUser_WhenExists() {
        when(userRepository.existsById(uuid)).thenReturn(true);

        userService.delete(uuid);

        verify(userRepository).deleteById(uuid);
    }

    /**
     * This test verifies that the delete method of UserService throws an exception
     * when trying to delete a user that does not exist in the repository.
     */
    @Test
    public void TEST_DELETE_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.existsById(uuid)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.delete(uuid));
        assertEquals("User not found with UUID: " + uuid, exception.getMessage());
    }
}
