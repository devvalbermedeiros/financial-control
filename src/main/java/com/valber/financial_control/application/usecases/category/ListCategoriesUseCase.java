package com.valber.financial_control.application.usecases.category;

import com.valber.financial_control.domain.entity.Category;

import java.util.List;

public interface ListCategoriesUseCase {
    List<Category> listAllCategories();
}
