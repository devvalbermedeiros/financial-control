package com.valber.financial_control.application.usecases.user;

import com.valber.financial_control.domain.entity.User;
import com.valber.financial_control.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoadUserByUsernameUseCaseImpl implements LoadUserByUsernameUseCase {

    private final UserRepository userRepository;

    public LoadUserByUsernameUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}