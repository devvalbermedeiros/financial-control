package com.valber.financial_control.application.usecases.transaction;

import com.valber.financial_control.domain.entity.Debt;

public interface CreateDebtUseCase {
    Debt createDebt(Debt debt);
}
