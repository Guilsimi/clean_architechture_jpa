package com.example.cleanarchmodel.infra.config;

import com.example.cleanarchmodel.core.repositories.UserRepository;
import com.example.cleanarchmodel.core.usecases.createUser.CreateUserUseCase;
import com.example.cleanarchmodel.core.usecases.createUser.CreateUserUseCaseImpl;
import com.example.cleanarchmodel.core.usecases.deleteUser.DeleteUserByIdUseCase;
import com.example.cleanarchmodel.core.usecases.deleteUser.DeleteUserByIdUseCaseImpl;
import com.example.cleanarchmodel.core.usecases.findUserByEmail.FindUserByEmailUseCase;
import com.example.cleanarchmodel.core.usecases.findUserByEmail.FindUserByEmailUseCaseImpl;
import com.example.cleanarchmodel.core.usecases.updateUser.UpdateUserUseCase;
import com.example.cleanarchmodel.core.usecases.updateUser.UpdateUserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository) {
        return new CreateUserUseCaseImpl(userRepository);
    }

    @Bean
    public DeleteUserByIdUseCase deleteUserByIdUseCase(UserRepository userRepository) {
        return new DeleteUserByIdUseCaseImpl(userRepository);
    }

    @Bean
    public FindUserByEmailUseCase findUserByEmailUseCase(UserRepository userRepository) {
        return new FindUserByEmailUseCaseImpl(userRepository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepository userRepository) {
        return new UpdateUserUseCaseImpl(userRepository);
    }

}
