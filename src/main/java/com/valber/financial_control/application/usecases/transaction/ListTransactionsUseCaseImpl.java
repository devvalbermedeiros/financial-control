
package com.valber.financial_control.application.usecases.transaction;

import com.valber.financial_control.domain.entity.Debt;
import com.valber.financial_control.domain.entity.Revenue;
import com.valber.financial_control.domain.entity.Transaction;
import com.valber.financial_control.domain.repository.DebtRepository;
import com.valber.financial_control.domain.repository.RevenueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ListTransactionsUseCaseImpl implements ListTransactionsUseCase {

    private final RevenueRepository revenueRepository;
    private final DebtRepository debtRepository;

    public ListTransactionsUseCaseImpl(RevenueRepository revenueRepository, DebtRepository debtRepository) {
        this.revenueRepository = revenueRepository;
        this.debtRepository = debtRepository;
    }

    @Override
    @PreAuthorize("principal.username == #userId")
    public List<Transaction> listTransactions(String userId, Month month, Year year) {
        log.debug("Listing transactions for user: {}", userId);
        LocalDate startDate = year.atMonth(month).atDay(1);
        LocalDate endDate = year.atMonth(month).atEndOfMonth();

        List<Revenue> revenues = revenueRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        log.debug("revenues: {}", revenues);
        List<Debt> debts = debtRepository.findByUserIdAndDateRange(userId, startDate, endDate);
        log.debug("debts: {}", debts);

        return Stream.concat(revenues.stream(), debts.stream())
                .sorted(Comparator.comparing(Transaction::getDate))
                .collect(Collectors.toList());
    }
}

