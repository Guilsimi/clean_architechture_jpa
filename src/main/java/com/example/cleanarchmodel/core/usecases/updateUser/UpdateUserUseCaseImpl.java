package com.example.cleanarchmodel.core.usecases.updateUser;

import com.example.cleanarchmodel.core.domain.user.User;
import com.example.cleanarchmodel.core.domain.user.UpdateUserCommand;
import com.example.cleanarchmodel.core.exceptions.UserNotFoundException;
import com.example.cleanarchmodel.core.repositories.UserRepository;

import java.util.UUID;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepository userRepository;

    public UpdateUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(UUID userId, UpdateUserCommand newData) {
            User user = this.getUserById(userId);
            user.updateData(newData.firstName(), newData.lastName(), newData.password());
            userRepository.update(user);
    }

    private User getUserById(UUID userId) {
        return userRepository.getById(userId).orElseThrow(
                () -> new UserNotFoundException(
                        "User could not be found.")
        );
    }


}
