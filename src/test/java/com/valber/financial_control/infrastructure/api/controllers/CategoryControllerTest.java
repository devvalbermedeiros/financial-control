
package com.valber.financial_control.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valber.financial_control.domain.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCategory_shouldReturnCreated() throws Exception {
        Category category = new Category("1", "Food", "Expenses for food");

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Food"));
    }

    @Test
    void getCategoryById_shouldReturnCategory() throws Exception {
        Category category = new Category("2", "Travel", "Expenses for travel");

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/categories/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Travel"));
    }

    @Test
    void getCategoryById_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/categories/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listAllCategories_shouldReturnListOfCategories() throws Exception {
        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
