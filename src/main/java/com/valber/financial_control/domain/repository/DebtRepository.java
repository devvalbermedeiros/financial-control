package com.valber.financial_control.domain.repository;

import com.valber.financial_control.domain.entity.Debt;
import com.valber.financial_control.domain.entity.InstallmentDebt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DebtRepository extends MongoRepository<Debt, String> {

    List<Debt> findByUserId(String userId);

    @Query("{ 'userId' : ?0, $or: [ { 'nextDueDate' : { $gte: ?1, $lte: ?2 } }, { 'startDate' : { $gte: ?1, $lte: ?2 } } ] }")
    List<Debt> findByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate);

    @Query("{ 'userId' : ?0, 'currentInstallment' : { $lt: '$totalInstallments' }, 'nextDueDate' : { $gte: ?1, $lte: ?2 } }")
    List<InstallmentDebt> findUpcomingInstallmentDebts(String userId, LocalDate today, LocalDate twoDaysLater);
}
