package com.example.cleanarchmodel.core.repositories;

import com.example.cleanarchmodel.core.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> getByEmail(String email);

    User update(User user);

    void deleteById(UUID id);

    Optional<User> getById(UUID id);

    boolean existsByEmail(String email);

    boolean existsById(UUID id);
}
