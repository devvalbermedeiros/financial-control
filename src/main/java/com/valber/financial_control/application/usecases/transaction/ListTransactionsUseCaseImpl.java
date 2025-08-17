package com.valber.financial_control.application.usecases.transaction;

import com.valber.financial_control.domain.entity.Debt;
import com.valber.financial_control.domain.entity.Revenue;
import com.valber.financial_control.domain.entity.Transaction;
import com.valber.financial_control.domain.repository.DebtRepository;
import com.valber.financial_control.domain.repository.RevenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ListTransactionsUseCaseImpl implements ListTransactionsUseCase {

    private final RevenueRepository revenueRepository;
    private final DebtRepository debtRepository;

    public ListTransactionsUseCaseImpl(RevenueRepository revenueRepository, DebtRepository debtRepository) {
        this.revenueRepository = revenueRepository;
        this.debtRepository = debtRepository;
    }

    @Override
    @PreAuthorize("principal.id == #userId")
    public List<Transaction> listTransactions(String userId, Month month, Year year) {
        LocalDate startDate = year.atMonth(month).atDay(1);
        LocalDate endDate = year.atMonth(month).atEndOfMonth();

        List<Revenue> revenues = revenueRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        List<Debt> debts = debtRepository.findByUserIdAndDateRange(userId, startDate, endDate);

        return Stream.concat(revenues.stream(), debts.stream())
                .sorted((t1, t2) -> t1.getDate().compareTo(t2.getDate()))
                .collect(Collectors.toList());
    }
}
