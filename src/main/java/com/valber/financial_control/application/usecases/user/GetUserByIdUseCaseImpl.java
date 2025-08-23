package com.valber.financial_control.application.usecases.user;

import com.valber.financial_control.domain.entity.User;
import com.valber.financial_control.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;


@Service
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {

    private final UserRepository userRepository;

    public GetUserByIdUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("principal.username == #id or hasRole('ADMIN')")
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }
}
