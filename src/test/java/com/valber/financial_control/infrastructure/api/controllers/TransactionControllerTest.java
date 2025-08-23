
package com.valber.financial_control.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valber.financial_control.domain.entity.Category;
import com.valber.financial_control.domain.entity.InstallmentDebt;
import com.valber.financial_control.domain.entity.Revenue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "testuser", roles = {"ADMIN"})
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addRevenue_shouldReturnCreated() throws Exception {
        Revenue revenue = new Revenue("1", "Salary", new BigDecimal("5000.00"), LocalDate.now(), "testuser");

        mockMvc.perform(post("/api/v1/transactions/revenues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(revenue)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Salary"));
    }

    @Test
    void createDebt_shouldReturnCreated() throws Exception {
        Category category = new Category("cat1", "Loans", "Personal loans");
        InstallmentDebt debt = new InstallmentDebt(new BigDecimal("10000.00"), 1, 1, new BigDecimal("1000.00"), LocalDate.now());
        debt.setUserId("testuser");
        debt.setCategory(category);
        debt.setDescription("Car Loan");

        mockMvc.perform(post("/api/v1/transactions/debts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(debt)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Car Loan"));
    }

    @Test
    void listTransactions_shouldReturnListOfTransactions() throws Exception {
        mockMvc.perform(get("/api/v1/transactions")
                        .param("userId", "testuser")
                        .param("month", Month.AUGUST.toString())
                        .param("year", Year.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

}
