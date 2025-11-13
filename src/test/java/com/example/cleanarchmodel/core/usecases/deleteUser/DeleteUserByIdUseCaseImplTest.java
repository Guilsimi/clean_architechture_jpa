package com.example.cleanarchmodel.core.usecases.deleteUser;

import com.example.cleanarchmodel.core.exceptions.UserNotFoundException;
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
class DeleteUserByIdUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserByIdUseCaseImpl deleteUserByIdUseCase;

    @Test
    @DisplayName("Delete user when id is valid in db")
    void deleteUserSuccessfully() {
        UUID uuid = UUID.randomUUID();

        when(this.userRepository.existsById(uuid)).thenReturn(true);

        doAnswer(invocation -> {
            when(this.userRepository.existsById(uuid)).thenReturn(false);
            return null;
        }).when(this.userRepository).deleteById(uuid);

        this.deleteUserByIdUseCase.execute(uuid);

        verify(this.userRepository, times(1)).existsById(any(UUID.class));
        verify(this.userRepository, times(1)).deleteById(any(UUID.class));
        verifyNoMoreInteractions(this.userRepository);

        assertFalse(this.userRepository.existsById(uuid));
    }

    @Test
    @DisplayName("Throw error when id is invalid in db")
    void throwErrorWhenUserIdIsInvalid() {
        UUID uuid = UUID.randomUUID();

        when(this.userRepository.existsById(uuid)).thenReturn(false);

        RuntimeException exception = assertThrows(UserNotFoundException.class, () -> {
           this.deleteUserByIdUseCase.execute(uuid);
        });

        assertEquals("User not found.", exception.getMessage());
    }

}