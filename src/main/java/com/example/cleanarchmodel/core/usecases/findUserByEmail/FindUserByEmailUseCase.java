package com.example.cleanarchmodel.core.usecases.findUserByEmail;

import com.example.cleanarchmodel.core.domain.user.User;
import com.example.cleanarchmodel.core.domain.user.UserResponseDTO;

public interface FindUserByEmailUseCase {

    UserResponseDTO execute(String email);

}
