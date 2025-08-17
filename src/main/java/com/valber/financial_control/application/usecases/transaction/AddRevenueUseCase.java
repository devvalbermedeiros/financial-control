package com.valber.financial_control.application.usecases.transaction;

import com.valber.financial_control.domain.entity.Revenue;

public interface AddRevenueUseCase {
    Revenue addRevenue(Revenue revenue);
}
