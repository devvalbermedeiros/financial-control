package com.valber.financial_control.application.kafka.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebtNotificationMessage {
    private String userId;
    private String username;
    private String debtId;
    private String description;
    private BigDecimal amount;
    private LocalDate dueDate;
    private Integer currentInstallment;
    private Integer totalInstallments;
}
