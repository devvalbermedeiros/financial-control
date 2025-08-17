package com.valber.financial_control.application.usecases.transaction;

import com.valber.financial_control.domain.entity.Transaction;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface ListTransactionsUseCase {
    List<Transaction> listTransactions(String userId, Month month, Year year);
}
