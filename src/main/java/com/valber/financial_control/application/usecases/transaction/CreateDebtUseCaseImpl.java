package com.valber.financial_control.application.usecases.transaction;

import com.valber.financial_control.domain.entity.Debt;
import com.valber.financial_control.domain.repository.DebtRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;


@Service
public class CreateDebtUseCaseImpl implements CreateDebtUseCase {

    private final DebtRepository debtRepository;

    public CreateDebtUseCaseImpl(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    @Override
    @PreAuthorize("principal.id == #debt.userId")
    public Debt createDebt(Debt debt) {
        if (debt.getCategory() == null) {
            throw new IllegalArgumentException("Debt must have a category.");
        }
        return debtRepository.save(debt);
    }
}
