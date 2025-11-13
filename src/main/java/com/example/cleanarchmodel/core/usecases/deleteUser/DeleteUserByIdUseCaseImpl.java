package com.example.cleanarchmodel.core.usecases.deleteUser;

import com.example.cleanarchmodel.core.exceptions.UserNotFoundException;
import com.example.cleanarchmodel.core.repositories.UserRepository;

import java.util.UUID;

public class DeleteUserByIdUseCaseImpl implements DeleteUserByIdUseCase {

    private final UserRepository userRepository;

    public DeleteUserByIdUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(UUID id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found.");
        }

        userRepository.deleteById(id);
    }

}
