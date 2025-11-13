package com.example.cleanarchmodel.core.usecases.createUser;

import com.example.cleanarchmodel.core.domain.user.User;
import com.example.cleanarchmodel.core.domain.user.UserRequestDTO;
import com.example.cleanarchmodel.core.exceptions.UserAlreadyExistsException;
import com.example.cleanarchmodel.core.exceptions.UserOperationException;
import com.example.cleanarchmodel.core.repositories.UserRepository;

public class CreateUserUseCaseImpl implements CreateUserUseCase{

    private final UserRepository userRepository;

    public CreateUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(UserRequestDTO userDto) {
        this.validateFields(userDto);

        User user = new User(userDto);
        this.userRepository.save(user);
    }

    private void validateFields(UserRequestDTO userDto) {
        if (userDto.email() != null && userDto.password() != null) {
            if (this.userRepository.existsByEmail(userDto.email())) {
                throw new UserAlreadyExistsException("User with this email already exists.");
            }

            if (userDto.password().length() < 8 || userDto.password().length() > 25) {
                throw new UserOperationException("Password must be at least 8 characters long and at most 25 characters long.");
            }

            if (!userDto.email().contains("@") || !userDto.email().contains(".") || userDto.email().length() < 5) {
                throw new UserOperationException("Email must be valid.");
            }
        }

    }
}
