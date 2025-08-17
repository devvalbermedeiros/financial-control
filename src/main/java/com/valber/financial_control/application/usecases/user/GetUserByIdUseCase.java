package com.valber.financial_control.application.usecases.user;

import com.valber.financial_control.domain.entity.User;

public interface GetUserByIdUseCase {
    User getUserById(String id);
}
