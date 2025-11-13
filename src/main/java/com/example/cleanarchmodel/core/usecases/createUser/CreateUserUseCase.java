package com.example.cleanarchmodel.core.usecases.createUser;

import com.example.cleanarchmodel.core.domain.user.CreateUserCommand;

public interface CreateUserUseCase {

    void execute(CreateUserCommand createUserCommand);

}
