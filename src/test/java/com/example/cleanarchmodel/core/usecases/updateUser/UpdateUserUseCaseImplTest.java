package com.example.cleanarchmodel.core.usecases.updateUser;

import com.example.cleanarchmodel.core.domain.user.User;
import com.example.cleanarchmodel.core.domain.user.UpdateUserCommand;
import com.example.cleanarchmodel.core.exceptions.UserNotFoundException;
import com.example.cleanarchmodel.core.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    @DisplayName("Update an existing user successfully")
    void updateUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        User userObject = new User(userId, "Test", "User", "testuser@email.com", "password123");
        UpdateUserCommand updateUserCommand = new UpdateUserCommand("UpdateTest", "UpdateUser", null);

        when(this.userRepository.getById(userId)).thenReturn(Optional.of(userObject));
        when(this.userRepository.update(userObject)).then(invocation -> {
            userObject.updateData(updateUserCommand.firstName(), updateUserCommand.lastName(), updateUserCommand.password());
            return null;
        });

        this.updateUserUseCase.execute(userId, updateUserCommand);

        verify(this.userRepository, times(1)).getById(any(UUID.class));
        verify(this.userRepository, times(1)).update(any(User.class));
        verifyNoMoreInteractions(this.userRepository);

        assertEquals("UpdateTest", userObject.getFirstName());
        assertEquals("UpdateUser", userObject.getLastName());
        assertNotNull(userObject.getPassword());
    }

    @Test
    @DisplayName("Throw exception when user is not found in db")
    void throwExceptionWhenUserNotFound() {
        when(this.userRepository.getById(any(UUID.class))).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(UserNotFoundException.class, () -> {
            this.updateUserUseCase.execute(UUID.randomUUID(), new UpdateUserCommand("", "", ""));
        });

        assertEquals("User could not be found.", exception.getMessage());
    }

}