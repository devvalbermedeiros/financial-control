package com.valber.financial_control.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "revenues")
@CompoundIndexes({
        @CompoundIndex(name = "userId_date_idx", def = "{'userId': 1, 'date': 1}", background = true)
})
public class Revenue implements Transaction {

    @Id
    private String id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private String userId;
}
