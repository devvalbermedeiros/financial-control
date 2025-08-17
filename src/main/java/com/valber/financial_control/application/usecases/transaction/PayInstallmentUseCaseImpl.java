package com.valber.financial_control.application.usecases.transaction;

import com.valber.financial_control.domain.entity.Debt;
import com.valber.financial_control.domain.entity.InstallmentDebt;
import com.valber.financial_control.domain.repository.DebtRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PostAuthorize;

@Service
public class PayInstallmentUseCaseImpl implements PayInstallmentUseCase {

    private final DebtRepository debtRepository;

    public PayInstallmentUseCaseImpl(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    @Override
    @PostAuthorize("returnObject.userId == principal.id")
    public InstallmentDebt payInstallment(String debtId) {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new IllegalArgumentException("Debt not found with id: " + debtId));

        if (!(debt instanceof InstallmentDebt)) {
            throw new IllegalArgumentException("Debt with id: " + debtId + " is not an installment debt.");
        }

        InstallmentDebt installmentDebt = (InstallmentDebt) debt;

        if (installmentDebt.getCurrentInstallment() >= installmentDebt.getTotalInstallments()) {
            throw new IllegalStateException("All installments for this debt have already been paid.");
        }

        installmentDebt.setCurrentInstallment(installmentDebt.getCurrentInstallment() + 1);
        installmentDebt.setNextDueDate(installmentDebt.getNextDueDate().plusMonths(1));

        return debtRepository.save(installmentDebt);
    }
}
