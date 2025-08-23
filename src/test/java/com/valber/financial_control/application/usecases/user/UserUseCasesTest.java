
package com.valber.financial_control.application.usecases.user;

import com.valber.financial_control.domain.entity.User;
import com.valber.financial_control.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCasesTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @InjectMocks
    private GetUserByIdUseCaseImpl getUserByIdUseCase;

    @Test
    void createUser_shouldEncodePasswordAndSaveUser() {
        User user = new User("1", "testuser", "password", List.of("USER"));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = createUserUseCase.createUser(user);

        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById_shouldReturnUser() {
        User user = new User("1", "testuser", "password", List.of("USER"));
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User foundUser = getUserByIdUseCase.getUserById("1");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> getUserByIdUseCase.getUserById("1"));

        String expectedMessage = "User not found with id: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
