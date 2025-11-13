package com.example.cleanarchmodel.core.domain.user;

import java.util.UUID;

public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(UUID id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(UserRequestDTO userRequestDTO) {
        this.id = null;
        this.firstName = userRequestDTO.firstName();
        this.lastName = userRequestDTO.lastName();
        this.email = userRequestDTO.email();
        this.password = userRequestDTO.password();
    }

    public User() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void updateData(UserUpdateData newData) {
        if (newData.firstName() != null) {
            this.firstName = newData.firstName();
        }
        if (newData.lastName() != null) {
            this.lastName = newData.lastName();
        }
        if (newData.password() != null) {
            this.password = newData.password();
        }
    }
}
