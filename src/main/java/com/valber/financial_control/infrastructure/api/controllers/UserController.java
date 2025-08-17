package com.valber.financial_control.infrastructure.api.controllers;

import com.valber.financial_control.application.usecases.user.CreateUserUseCase;
import com.valber.financial_control.application.usecases.user.GetUserByIdUseCase;
import com.valber.financial_control.application.usecases.user.ListUsersUseCase;
import com.valber.financial_control.domain.entity.User;
import com.valber.financial_control.infrastructure.api.UserApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final ListUsersUseCase listUsersUseCase;

    public UserController(CreateUserUseCase createUserUseCase,
                          GetUserByIdUseCase getUserByIdUseCase,
                          ListUsersUseCase listUsersUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.listUsersUseCase = listUsersUseCase;
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        User createdUser = createUserUseCase.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> getUserById(String id) {
        try {
            User user = getUserByIdUseCase.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = listUsersUseCase.listAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
