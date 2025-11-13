package com.example.cleanarchmodel.core.usecases.createUser;

import com.example.cleanarchmodel.core.domain.user.User;
import com.example.cleanarchmodel.core.domain.user.CreateUserCommand;
import com.example.cleanarchmodel.core.exceptions.UserAlreadyExistsException;
import com.example.cleanarchmodel.core.exceptions.UserOperationException;
import com.example.cleanarchmodel.core.repositories.UserRepository;

public class CreateUserUseCaseImpl implements CreateUserUseCase{

    private final UserRepository userRepository;

    public CreateUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(CreateUserCommand createUserCommand) {
        this.validateFields(createUserCommand);

        User user = new User(
                createUserCommand.firstName(),
                createUserCommand.lastName(),
                createUserCommand.email(),
                createUserCommand.password()
        );

        this.userRepository.save(user);
    }

    private void validateFields(CreateUserCommand createUserCommand) {
        if (createUserCommand.email() != null && createUserCommand.password() != null) {
            if (this.userRepository.existsByEmail(createUserCommand.email())) {
                throw new UserAlreadyExistsException("User with this email already exists.");
            }

            if (createUserCommand.password().length() < 8 || createUserCommand.password().length() > 25) {
                throw new UserOperationException("Password must be at least 8 characters long and at most 25 characters long.");
            }

            if (!createUserCommand.email().contains("@") || !createUserCommand.email().contains(".") || createUserCommand.email().length() < 5) {
                throw new UserOperationException("Email must be valid.");
            }
        }

    }
}
