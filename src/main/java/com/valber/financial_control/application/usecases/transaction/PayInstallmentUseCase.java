package com.valber.financial_control.application.usecases.transaction;

import com.valber.financial_control.domain.entity.InstallmentDebt;

public interface PayInstallmentUseCase {
    InstallmentDebt payInstallment(String debtId);
}
