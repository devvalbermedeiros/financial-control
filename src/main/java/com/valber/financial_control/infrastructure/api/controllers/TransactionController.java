package com.valber.financial_control.infrastructure.api.controllers;

import com.valber.financial_control.application.usecases.transaction.AddRevenueUseCase;
import com.valber.financial_control.application.usecases.transaction.CreateDebtUseCase;
import com.valber.financial_control.application.usecases.transaction.ListTransactionsUseCase;
import com.valber.financial_control.application.usecases.transaction.PayInstallmentUseCase;
import com.valber.financial_control.domain.entity.Debt;
import com.valber.financial_control.domain.entity.InstallmentDebt;
import com.valber.financial_control.domain.entity.Revenue;
import com.valber.financial_control.domain.entity.Transaction;
import com.valber.financial_control.infrastructure.api.TransactionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
public class TransactionController implements TransactionApi {

    private final AddRevenueUseCase addRevenueUseCase;
    private final CreateDebtUseCase createDebtUseCase;
    private final ListTransactionsUseCase listTransactionsUseCase;
    private final PayInstallmentUseCase payInstallmentUseCase;

    public TransactionController(AddRevenueUseCase addRevenueUseCase,
                                 CreateDebtUseCase createDebtUseCase,
                                 ListTransactionsUseCase listTransactionsUseCase,
                                 PayInstallmentUseCase payInstallmentUseCase) {
        this.addRevenueUseCase = addRevenueUseCase;
        this.createDebtUseCase = createDebtUseCase;
        this.listTransactionsUseCase = listTransactionsUseCase;
        this.payInstallmentUseCase = payInstallmentUseCase;
    }

    @Override
    public ResponseEntity<Revenue> addRevenue(Revenue revenue) {
        Revenue createdRevenue = addRevenueUseCase.addRevenue(revenue);
        return new ResponseEntity<>(createdRevenue, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Debt> createDebt(Debt debt) {
        Debt createdDebt = createDebtUseCase.createDebt(debt);
        return new ResponseEntity<>(createdDebt, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Transaction>> listTransactions(String userId, Month month, Year year) {
        List<Transaction> transactions = listTransactionsUseCase.listTransactions(userId, month, year);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InstallmentDebt> payInstallment(String debtId) {
        try {
            InstallmentDebt updatedDebt = payInstallmentUseCase.payInstallment(debtId);
            return new ResponseEntity<>(updatedDebt, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
