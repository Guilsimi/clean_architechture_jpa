package com.example.cleanarchmodel.core.domain.user;

public record CreateUserCommand(String firstName, String lastName, String email, String password) {
}
