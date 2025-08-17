package com.valber.financial_control.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Transaction {
    String getDescription();
    BigDecimal getAmount();
    LocalDate getDate();
}
