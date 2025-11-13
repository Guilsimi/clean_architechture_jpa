package com.example.cleanarchmodel.infra.adapters;

import com.example.cleanarchmodel.core.domain.user.User;
import com.example.cleanarchmodel.core.domain.user.UserUpdateData;
import com.example.cleanarchmodel.core.exceptions.UserAlreadyExistsException;
import com.example.cleanarchmodel.core.exceptions.UserOperationException;
import com.example.cleanarchmodel.infra.adapters.repositoriesAdapters.UserJpaRepositoryAdapter;
import com.example.cleanarchmodel.infra.mappers.UserMapper;
import com.example.cleanarchmodel.infra.persistence.UserJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserJpaRepositoryAdapterIntegrationTest {

    @Autowired
    private UserJpaRepositoryAdapter userJpaRepositoryAdapter;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should save and persist a valid user")
    void saveUserWhenItIsValid() {
        User userObject = new User(null, "User", "Test", "usertest@email.com", "password123");
        User userEntity = this.userJpaRepositoryAdapter.save(userObject);

        assertNotNull(userEntity.getId());
        assertTrue(this.userJpaRepository.findById(userEntity.getId()).isPresent());
        assertEquals(userEntity.getFirstName(), userObject.getFirstName());
        assertEquals(userEntity.getLastName(), userObject.getLastName());
        assertEquals(userEntity.getEmail(), userObject.getEmail());
        assertEquals(userEntity.getPassword(), userObject.getPassword());
    }

    @Test
    @DisplayName("Should not save a user if email field is missing")
    void notSaveUserWhenEmailFieldIsMissing() {
        User userObject = new User(null, "User", "Test", null, "password123");

        Exception exception = assertThrows(UserOperationException.class, () -> {
            this.userJpaRepositoryAdapter.save(userObject);
        });

         assertEquals("Required fields are missing", exception.getMessage());
    }

    @Test
    @DisplayName("Should get a existing user by email")
    void getUserByEmailWhenUserExists() {
       User userObject = new User(null, "User", "Test", "usertest@email.com", "password123");
       this.persistUser(userObject);

        Optional<User> foundUser = this.userJpaRepositoryAdapter.getByEmail("usertest@email.com");

        assertTrue(foundUser.isPresent());
        assertNotNull(foundUser.get().getId());
        assertEquals(userObject.getFirstName(), foundUser.get().getFirstName());
        assertEquals(userObject.getLastName(), foundUser.get().getLastName());
        assertEquals(userObject.getEmail(), foundUser.get().getEmail());
        assertEquals(userObject.getPassword(), foundUser.get().getPassword());
    }

    @Test
    @DisplayName("Should return empty when getting a non-existing user by email")
    void returnEmptyWhenNonExistingUserByEmail() {
        Optional<User> unfoundUser = this.userJpaRepositoryAdapter.getByEmail("invalidemail@email.com");

        assertTrue(unfoundUser.isEmpty());
    }

    @Test
    @DisplayName("Should update first name and last name of an existing user")
    void updateNameOfExistingUser() {
        User userObject = new User(null, "User", "Test", "usertest@email.com", "password123");
        this.persistUser(userObject);

        Optional<User> existingUser = userJpaRepository.findByEmail("usertest@email.com")
                .map(userMapper::toDomain);
        assertTrue(existingUser.isPresent());

        UserUpdateData newData = new UserUpdateData("UpdatedName", "UpdatedLastName", null);
        existingUser.get().updateData(newData);
        this.userJpaRepositoryAdapter.update(existingUser.get());

        Optional<User> updatedUser = userJpaRepository.findByEmail(existingUser.get().getEmail()).map(userMapper::toDomain);
        assertTrue(updatedUser.isPresent());
        assertEquals("UpdatedName", updatedUser.get().getFirstName());
        assertEquals("UpdatedLastName", updatedUser.get().getLastName());
        assertNotNull(updatedUser.get().getEmail());
    }

    @Test
    @DisplayName("Should delete an existing user by id")
    void deleteExistingUserById() {
        User userObject = new User(null, "User", "Test", "usertest@email.com", "password123");
        this.persistUser(userObject);

        Optional<User> existingUser = userJpaRepository.findByEmail("usertest@email.com")
                .map(userMapper::toDomain);
        assertTrue(existingUser.isPresent());

        this.userJpaRepositoryAdapter.deleteById(existingUser.get().getId());
        Optional<User> unexistentUser = userJpaRepository.findByEmail("usertest@email.com")
                .map(userMapper::toDomain);

        assertTrue(unexistentUser.isEmpty());
    }

    @Test
    @DisplayName("Should get user by id when user exists")
    void getUserByIdWhenUserExists() {
        User userObject = new User(null, "User", "Test", "usertest@email.com", "password123");
        this.persistUser(userObject);

        Optional<User> existingUser = this.userJpaRepository.findByEmail(userObject.getEmail())
                .map(userMapper::toDomain);
        assertTrue(existingUser.isPresent());

        UUID userId = existingUser.get().getId();

        Optional<User> foundUser = this.userJpaRepositoryAdapter.getById(userId);

        assertTrue(foundUser.isPresent());
        assertNotNull(foundUser.get().getId());
        assertEquals(userObject.getFirstName(), foundUser.get().getFirstName());
        assertEquals(userObject.getLastName(), foundUser.get().getLastName());
        assertEquals(userObject.getEmail(), foundUser.get().getEmail());
        assertEquals(userObject.getPassword(), foundUser.get().getPassword());
    }

    @Test
    @DisplayName("Should return empty when getting a non-existing user by id")
    void returnEmptyWhenNonExistingUserById() {

        UUID userId = UUID.randomUUID();

        Optional<User> foundUser = this.userJpaRepositoryAdapter.getById(userId);

        assertTrue(foundUser.isEmpty());
    }

    @Test
    @DisplayName("Should return true when checking existence of user by email that exists")
    void returnTrueWhenUserExistsByEmail() {
        User userObject = new User(null, "User", "Test", "usertest@email.com", "password123");
        this.persistUser(userObject);

        boolean exists = this.userJpaRepositoryAdapter.existsByEmail("usertest@email.com");

        assertTrue(exists);
    }

    @Test
    @DisplayName("Should return false when checking existence of user by email that not exists")
    void returnFalseWhenUserNotExistsByEmail() {
        boolean exists = this.userJpaRepositoryAdapter.existsByEmail("usertest@email.com");
        assertFalse(exists);
    }

    @Test
    @DisplayName("Should return true when checking existence of user by id that exists")
    void returnTrueWhenUserExistsById() {
        User userObject = new User(null, "User", "Test", "usertest@email.com", "password123");
        this.persistUser(userObject);

        Optional<User> existingUser = this.userJpaRepository.findByEmail(userObject.getEmail())
                .map(userMapper::toDomain);
        assertTrue(existingUser.isPresent());

        boolean exists = this.userJpaRepositoryAdapter.existsById(existingUser.get().getId());
        assertTrue(exists);
    }

    @Test
    @DisplayName("Should return false when checking existence of user by id that not exists")
    void returnFalseWhenUserNotExistsById() {
        boolean exists = this.userJpaRepositoryAdapter.existsById(UUID.randomUUID());
        assertFalse(exists);
    }

    private void persistUser(User user) {
        this.entityManager.persist(
            this.userMapper.toEntity(user)
        );

        this.entityManager.flush();
    }
}