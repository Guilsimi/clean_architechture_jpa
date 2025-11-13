package com.example.cleanarchmodel.core.usecases.findUserByEmail;

import com.example.cleanarchmodel.core.domain.user.User;
import com.example.cleanarchmodel.core.domain.user.UserResult;
import com.example.cleanarchmodel.core.exceptions.UserNotFoundException;
import com.example.cleanarchmodel.core.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserByEmailUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserByEmailUseCaseImpl findUserByEmailUseCase;

    @Test
    @DisplayName("Find and return using email an existing user in db")
    void findValidUserByEmail() {
        User user = new User(UUID.randomUUID(), "Test", "User", "testuser@email.com", "password123");

        when(this.userRepository.getByEmail("testuser@email.com")).thenReturn(Optional.of(user));

        UserResult userResult = this.findUserByEmailUseCase.execute(user.getEmail());

        verify(userRepository, times(1)).getByEmail(any(String.class));
        assertEquals(userResult.firstName(), user.getFirstName());
        assertEquals(userResult.lastName(), user.getLastName());
        assertEquals(userResult.email(), user.getEmail());
    }

    @Test
    @DisplayName("Throw error when email don't exists in db")
    void throwErrorToInexistentEmail() {
        String email = "invalid@email.com";

        when(this.userRepository.getByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(UserNotFoundException.class, () -> {
            this.findUserByEmailUseCase.execute(email);
        });

        assertEquals("Cannot find a user with the provided email.", exception.getMessage());
    }

}