package com.fitness.userService.service;

import com.fitness.userService.dto.RegisterUserRequest;
import com.fitness.userService.dto.UserResponse;
import com.fitness.userService.model.User;
import com.fitness.userService.model.UserRole;
import com.fitness.userService.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests {

    private final String EMAIL = "test@email.com";
    private final String PASSWORD = "Password1!";
    private final String FIRST_NAME = "First_name";
    private final String LAST_NAME = "Last_name";
    private final LocalDateTime CREATED_AT = LocalDateTime.of(2020, 10, 30,12,15);
    private final LocalDateTime UPDATED_AT = LocalDateTime.of(2020, 10, 30,12,15);
    private  final UserRole USER_ROLE = UserRole.USER;

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    public  void createUserTest(){
        User user = new User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setRole(USER_ROLE);
        user.setCreatedAt(CREATED_AT);
        user.setUpdatedAt(UPDATED_AT);

        when(userRepository.save(any())).thenReturn(user);

        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail(EMAIL);
        request.setPassword(PASSWORD);
        request.setFirstName(FIRST_NAME);
        request.setLastName(LAST_NAME);

        UserResponse response = userService.register(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(user.getId());
        assertThat(response.getEmail()).isEqualTo(EMAIL);
        assertThat(response.getPassword()).isEqualTo(PASSWORD);
        assertThat(response.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(response.getLastName()).isEqualTo(LAST_NAME);
        verify(userRepository).save(any());
    }

    @Test
    public void getUserProfile(){
        Optional<User> user = Optional.of(new User());
        user.get().setId("TEST_ID");
        user.get().setEmail(EMAIL);
        user.get().setPassword(PASSWORD);
        user.get().setFirstName(FIRST_NAME);
        user.get().setLastName(LAST_NAME);
        user.get().setRole(USER_ROLE);
        user.get().setCreatedAt(CREATED_AT);
        user.get().setUpdatedAt(UPDATED_AT);

        when(userRepository.findById("TEST_ID")).thenReturn(user);

        UserResponse response = userService.getUserProfile("TEST_ID");
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("TEST_ID");
        assertThat(response.getEmail()).isEqualTo(EMAIL);
        assertThat(response.getPassword()).isEqualTo(PASSWORD);
        assertThat(response.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(response.getLastName()).isEqualTo(LAST_NAME);
        verify(userRepository).findById("TEST_ID");
    }
}
