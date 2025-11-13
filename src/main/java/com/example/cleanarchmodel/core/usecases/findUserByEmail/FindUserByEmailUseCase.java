package com.example.cleanarchmodel.core.usecases.findUserByEmail;

import com.example.cleanarchmodel.core.domain.user.UserResult;

public interface FindUserByEmailUseCase {

    UserResult execute(String email);

}
