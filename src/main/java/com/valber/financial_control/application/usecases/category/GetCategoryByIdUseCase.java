package com.valber.financial_control.application.usecases.category;

import com.valber.financial_control.domain.entity.Category;

public interface GetCategoryByIdUseCase {
    Category getCategoryById(String id);
}
