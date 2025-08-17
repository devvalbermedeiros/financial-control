package com.valber.financial_control.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "debts")
public class InstallmentDebt extends Debt {

    private BigDecimal totalAmount;
    private Integer totalInstallments;
    private Integer currentInstallment;
    private BigDecimal installmentAmount;
    @Indexed(background = true)
    private LocalDate nextDueDate;

    @Override
    public BigDecimal getAmount() {
        return installmentAmount;
    }

    @Override
    public LocalDate getDate() {
        return nextDueDate;
    }
}
