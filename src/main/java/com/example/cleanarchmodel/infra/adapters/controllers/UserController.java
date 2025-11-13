package com.example.cleanarchmodel.infra.adapters.controllers;

import com.example.cleanarchmodel.core.domain.user.CreateUserCommand;
import com.example.cleanarchmodel.core.domain.user.UserResult;
import com.example.cleanarchmodel.core.domain.user.UpdateUserCommand;
import com.example.cleanarchmodel.core.usecases.createUser.CreateUserUseCase;
import com.example.cleanarchmodel.core.usecases.deleteUser.DeleteUserByIdUseCase;
import com.example.cleanarchmodel.core.usecases.findUserByEmail.FindUserByEmailUseCase;
import com.example.cleanarchmodel.core.usecases.updateUser.UpdateUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserByIdUseCase deleteUserByIdUseCase;
    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase,
                          DeleteUserByIdUseCase deleteUserByIdUseCase,
                          FindUserByEmailUseCase findUserByEmailUseCase,
                          UpdateUserUseCase updateUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.deleteUserByIdUseCase = deleteUserByIdUseCase;
        this.findUserByEmailUseCase = findUserByEmailUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUser(@RequestBody CreateUserCommand requestDTO) {
        this.createUserUseCase.execute(requestDTO);
    }

    @DeleteMapping("/delete/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID userId) {
        this.deleteUserByIdUseCase.execute(userId);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public UserResult getUserByEmail(@RequestParam String email) {
        return this.findUserByEmailUseCase.execute(email);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserUsingId(@PathVariable UUID id, @RequestBody UpdateUserCommand userNewData) {
        this.updateUserUseCase.execute(id, userNewData);
    }
}
