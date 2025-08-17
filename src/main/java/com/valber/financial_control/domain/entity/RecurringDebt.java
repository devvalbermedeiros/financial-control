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
public class RecurringDebt extends Debt {

    private BigDecimal amount;
    private RecurrencePeriod period;
    @Indexed(background = true)
    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public LocalDate getDate() {
        // Para uma dívida recorrente, a "data" pode ser a do próximo vencimento.
        // A lógica exata para calcular isso pode ser adicionada posteriormente.
        // Por enquanto, retornamos a data de início.
        return startDate;
    }
}
