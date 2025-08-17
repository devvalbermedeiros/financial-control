package com.valber.financial_control.application.usecases.category;

import com.valber.financial_control.domain.entity.Category;
import com.valber.financial_control.domain.repository.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCategoriesUseCaseImpl implements ListCategoriesUseCase {

    private final CategoryRepository categoryRepository;

    public ListCategoriesUseCaseImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Category> listAllCategories() {
        return categoryRepository.findAll();
    }
}
