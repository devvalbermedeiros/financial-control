package com.valber.financial_control.application.usecases.category;

import com.valber.financial_control.domain.entity.Category;

public interface CreateCategoryUseCase {
    Category createCategory(Category category);
}
