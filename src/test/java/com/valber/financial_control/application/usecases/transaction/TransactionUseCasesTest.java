
package com.valber.financial_control.application.usecases.transaction;

import com.valber.financial_control.domain.entity.*;
import com.valber.financial_control.domain.repository.DebtRepository;
import com.valber.financial_control.domain.repository.RevenueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionUseCasesTest {

    @Mock
    private RevenueRepository revenueRepository;

    @Mock
    private DebtRepository debtRepository;

    @InjectMocks
    private AddRevenueUseCaseImpl addRevenueUseCase;

    @InjectMocks
    private CreateDebtUseCaseImpl createDebtUseCase;

    @Test
    void addRevenue_shouldSaveRevenue() {
        Revenue revenue = new Revenue("1", "Salary", new BigDecimal("5000.00"), LocalDate.now(), "user1");
        when(revenueRepository.save(any(Revenue.class))).thenReturn(revenue);

        Revenue savedRevenue = addRevenueUseCase.addRevenue(revenue);

        assertNotNull(savedRevenue);
        assertEquals("Salary", savedRevenue.getDescription());
        verify(revenueRepository, times(1)).save(revenue);
    }

    @Test
    void addRevenue_shouldThrowException_forNonPositiveAmount() {
        Revenue revenue = new Revenue("1", "Invalid Revenue", new BigDecimal("0.00"), LocalDate.now(), "user1");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> addRevenueUseCase.addRevenue(revenue));

        String expectedMessage = "Revenue amount must be positive.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void createDebt_shouldSaveDebt() {
        Category category = new Category("cat1", "Loans", "Personal loans");
        Debt debt = new InstallmentDebt(new BigDecimal("10000.00"), 1, 1, new BigDecimal("1000.00"), LocalDate.now());
        debt.setCategory(category);
        debt.setDescription("Car Loan");
        when(debtRepository.save(any(Debt.class))).thenReturn(debt);

        Debt savedDebt = createDebtUseCase.createDebt(debt);

        assertNotNull(savedDebt);
        assertEquals("Car Loan", savedDebt.getDescription());
        verify(debtRepository, times(1)).save(debt);
    }

    @Test
    void createDebt_shouldThrowException_forNullCategory() {
        Debt debt = new InstallmentDebt(new BigDecimal("10000.00"), 1, 1, new BigDecimal("1000.00"), LocalDate.now());
        debt.setDescription("Car Loan");

        assertThrows(IllegalArgumentException.class, () -> createDebtUseCase.createDebt(debt));

    }

}
