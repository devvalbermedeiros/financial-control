package com.valber.financial_control.infrastructure.config.security;

import com.valber.financial_control.application.usecases.user.LoadUserByUsernameUseCase;
import com.valber.financial_control.domain.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoadUserByUsernameUseCase loadUserByUsernameUseCase;

    public UserDetailsServiceImpl(LoadUserByUsernameUseCase loadUserByUsernameUseCase) {
        this.loadUserByUsernameUseCase = loadUserByUsernameUseCase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loadUserByUsernameUseCase.loadUserByUsername(username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
