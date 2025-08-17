package com.valber.financial_control.application.usecases.user;

import com.valber.financial_control.domain.entity.User;
import com.valber.financial_control.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Service
public class ListUsersUseCaseImpl implements ListUsersUseCase {

    private final UserRepository userRepository;

    public ListUsersUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }
}
