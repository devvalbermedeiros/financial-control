
package com.valber.financial_control.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valber.financial_control.application.usecases.user.GetUserByIdUseCase;
import com.valber.financial_control.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN"}, username = "testuser")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Mock
    private GetUserByIdUseCase getUserByIdUseCase;

    @Test
    void createUser_shouldReturnCreated() throws Exception {
        User user = new User("1", "newuser", "password", List.of("USER"));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedpassword");
        when(getUserByIdUseCase.getUserById(anyString())).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        User user = new User("2", "testuser2", "password", List.of("USER"));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedpassword");
        when(getUserByIdUseCase.getUserById(anyString())).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser2"));
    }

    @Test
    void getUserById_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listAllUsers_shouldReturnListOfUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
