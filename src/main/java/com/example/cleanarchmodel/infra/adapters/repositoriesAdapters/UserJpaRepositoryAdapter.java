package com.example.cleanarchmodel.infra.adapters.repositoriesAdapters;

import com.example.cleanarchmodel.core.domain.user.User;
import com.example.cleanarchmodel.core.exceptions.UserAlreadyExistsException;
import com.example.cleanarchmodel.core.exceptions.UserOperationException;
import com.example.cleanarchmodel.core.repositories.UserRepository;
import com.example.cleanarchmodel.infra.mappers.UserMapper;
import com.example.cleanarchmodel.infra.persistence.UserJpaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserJpaRepositoryAdapter implements UserRepository {

    UserJpaRepository userJpaRepository;
    UserMapper userMapper;

    public UserJpaRepositoryAdapter(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        try {
            return userMapper.toDomain(
                    userJpaRepository.saveAndFlush(
                            userMapper.toEntity(user)
                    ));
        } catch (DataIntegrityViolationException e) {
            String errorCauseMessage = e.getMostSpecificCause().getMessage();

            if (errorCauseMessage.contains("not-null")) {
                throw new UserOperationException("Required fields are missing");
            }

            throw new UserOperationException("An error occurred while saving the user");
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public User update(User user) {
        return userMapper.toDomain(userJpaRepository.save(
                userMapper.toEntity(user)
        ));
    }

    @Override
    public void deleteById(UUID id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public Optional<User> getById(UUID id) {
        return userJpaRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(UUID id) {
        return userJpaRepository.existsById(id);
    }
}
