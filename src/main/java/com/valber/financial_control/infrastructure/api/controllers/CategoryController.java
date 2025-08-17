package com.valber.financial_control.infrastructure.api.controllers;

import com.valber.financial_control.application.usecases.category.CreateCategoryUseCase;
import com.valber.financial_control.application.usecases.category.GetCategoryByIdUseCase;
import com.valber.financial_control.application.usecases.category.ListCategoriesUseCase;
import com.valber.financial_control.domain.entity.Category;
import com.valber.financial_control.infrastructure.api.CategoryApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase,
                              GetCategoryByIdUseCase getCategoryByIdUseCase,
                              ListCategoriesUseCase listCategoriesUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
        this.listCategoriesUseCase = listCategoriesUseCase;
    }

    @Override
    public ResponseEntity<Category> createCategory(Category category) {
        Category createdCategory = createCategoryUseCase.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Category> getCategoryById(String id) {
        try {
            Category category = getCategoryByIdUseCase.getCategoryById(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Category>> listAllCategories() {
        List<Category> categories = listCategoriesUseCase.listAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
