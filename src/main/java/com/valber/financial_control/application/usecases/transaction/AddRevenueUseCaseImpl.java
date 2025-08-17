package com.valber.financial_control.application.usecases.transaction;

import com.valber.financial_control.domain.entity.Revenue;
import com.valber.financial_control.domain.repository.RevenueRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AddRevenueUseCaseImpl implements AddRevenueUseCase {

    private final RevenueRepository revenueRepository;

    public AddRevenueUseCaseImpl(RevenueRepository revenueRepository) {
        this.revenueRepository = revenueRepository;
    }

    @Override
    @PreAuthorize("principal.id == #revenue.userId")
    public Revenue addRevenue(Revenue revenue) {
        if (revenue.getAmount() == null || revenue.getAmount().signum() <= 0) {
            throw new IllegalArgumentException("Revenue amount must be positive.");
        }
        return revenueRepository.save(revenue);
    }
}
