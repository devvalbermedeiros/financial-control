package com.valber.financial_control.application.usecases.category;

import com.valber.financial_control.domain.entity.Category;
import com.valber.financial_control.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;

@Service
public class CreateCategoryUseCaseImpl implements CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public CreateCategoryUseCaseImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
}
