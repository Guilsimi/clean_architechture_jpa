package com.example.cleanarchmodel.core.usecases.updateUser;

import com.example.cleanarchmodel.core.domain.user.UpdateUserCommand;

import java.util.UUID;

public interface UpdateUserUseCase {

    void execute(UUID userId, UpdateUserCommand newData);

}
