package com.example.cleanarchmodel.core.usecases.createUser;

import com.example.cleanarchmodel.core.domain.user.User;
import com.example.cleanarchmodel.core.domain.user.CreateUserCommand;
import com.example.cleanarchmodel.core.exceptions.UserAlreadyExistsException;
import com.example.cleanarchmodel.core.exceptions.UserOperationException;
import com.example.cleanarchmodel.core.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    @DisplayName("Create a user when all datas is valid")
    void createUserSuccessfully() {
        UUID randomId = UUID.randomUUID();
        CreateUserCommand createUserCommand = new CreateUserCommand("User", "Test", "usertest@email.com", "password123");

        this.createUserUseCase.execute(createUserCommand);

        verify(this.userRepository, times(1)).existsByEmail(any(String.class));
        verify(this.userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(this.userRepository);
    }

    @Test
    @DisplayName("Throw error when user email exists in db")
    void throwErrorWhenUserEmailAlreadyExists() {
        CreateUserCommand createUserCommand = new CreateUserCommand("User", "Test", "usertest@email.com", "password123");
        when(this.userRepository.existsByEmail(any(String.class))).thenReturn(true);

        RuntimeException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            this.createUserUseCase.execute(createUserCommand);
        });

        verify(this.userRepository, times(1)).existsByEmail(any(String.class));
        verifyNoMoreInteractions(userRepository);

        assertEquals("User with this email already exists.", exception.getMessage());
    }

    @Test
    @DisplayName("Throw error when user password is less than 8 characters")
    void throwErrorWhenUserPasswordIsLessThan8Characters() {
        CreateUserCommand createUserCommand = new CreateUserCommand("User", "Test", "usertest@email.com", "pass");
        when(this.userRepository.existsByEmail(any(String.class))).thenReturn(false);

        RuntimeException exception = assertThrows(UserOperationException.class, () -> {
            this.createUserUseCase.execute(createUserCommand);
        });

        assertEquals("Password must be at least 8 characters long and at most 25 characters long.", exception.getMessage());
    }

    @Test
    @DisplayName("Throw error when user email is invalid")
    void throwErrorWhenUserEmailIsInvalid() {
        CreateUserCommand createUserCommand = new CreateUserCommand("User", "Test", "usertestemailcom", "password123");
        when(this.userRepository.existsByEmail(any(String.class))).thenReturn(false);

        RuntimeException exception = assertThrows(UserOperationException.class, () -> {
            this.createUserUseCase.execute(createUserCommand);
        });

        assertEquals("Email must be valid.", exception.getMessage());
    }

}