package com.example.cleanarchmodel.core.usecases.deleteUser;

import java.util.UUID;

public interface DeleteUserByIdUseCase {

    void execute(UUID id);

}