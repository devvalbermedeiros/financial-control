package com.valber.financial_control.application.usecases.user;

import com.valber.financial_control.domain.entity.User;

import java.util.List;

public interface ListUsersUseCase {
    List<User> listAllUsers();
}
