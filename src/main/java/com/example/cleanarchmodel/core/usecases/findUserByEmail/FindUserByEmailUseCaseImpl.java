package com.example.cleanarchmodel.core.usecases.findUserByEmail;

import com.example.cleanarchmodel.core.domain.user.User;
import com.example.cleanarchmodel.core.domain.user.UserResult;
import com.example.cleanarchmodel.core.exceptions.UserNotFoundException;
import com.example.cleanarchmodel.core.repositories.UserRepository;

public class FindUserByEmailUseCaseImpl implements FindUserByEmailUseCase {

    private final UserRepository userRepository;

    public FindUserByEmailUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResult execute(String email) {
        User user = userRepository.getByEmail(email).orElseThrow(
                () -> new UserNotFoundException(
                        "Cannot find a user with the provided email.")
        );

        return this.toResult(user);
    }

    private UserResult toResult(User user) {
        return new UserResult(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

}
