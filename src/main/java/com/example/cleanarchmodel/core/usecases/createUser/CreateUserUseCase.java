package com.example.cleanarchmodel.core.usecases.createUser;

import com.example.cleanarchmodel.core.domain.user.UserRequestDTO;

public interface CreateUserUseCase {

    void execute(UserRequestDTO userDto);

}
