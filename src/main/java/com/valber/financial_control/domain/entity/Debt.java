package com.valber.financial_control.domain.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = InstallmentDebt.class, name = "installment"),
        @JsonSubTypes.Type(value = RecurringDebt.class, name = "recurring")
})
public abstract class Debt implements Transaction {

    @Id
    private String id;
    private String description;
    private Category category;
    private String userId;

    @Override
    public abstract BigDecimal getAmount();

    @Override
    public abstract LocalDate getDate();
}
