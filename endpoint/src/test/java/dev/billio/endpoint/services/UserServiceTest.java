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

import java.util.Collections;
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
    private UserModel user;
    //endregion Fields

    /**
     * This method is executed before each test case to set up the initial state.
     * It creates a new UserModel instance with a random UUID and predefined values.
     */
    @BeforeEach
    void setUp() {
        UUID uuid = UUID.randomUUID();
        user = new UserModel(uuid, "test_user", "user@test.com", "password123", UserEnum.eUserType.COMPANY);
    }

    /**
     * This test verifies that the get method of UserService returns a paginated list of users.
     * It mocks the userRepository to return a Page containing a single user and checks that the
     * returned Page has the expected number of elements.
     */
    @Test
    public void TEST_GET_ShouldReturnPageOfUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserModel> page = new PageImpl<>(Collections.singletonList(user));

        when(userRepository.findAll(pageable)).thenReturn(page);

        Page<UserModel> result = userService.get(pageable);

        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findAll(pageable);
    }

}