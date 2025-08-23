
package com.valber.financial_control.application.usecases.category;

import com.valber.financial_control.domain.entity.Category;
import com.valber.financial_control.domain.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCasesTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CreateCategoryUseCaseImpl createCategoryUseCase;

    @InjectMocks
    private GetCategoryByIdUseCaseImpl getCategoryByIdUseCase;

    @Test
    void createCategory_shouldSaveCategory() {
        Category category = new Category("1", "Food", "Expenses for food");
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category savedCategory = createCategoryUseCase.createCategory(category);

        assertNotNull(savedCategory);
        assertEquals("Food", savedCategory.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void getCategoryById_shouldReturnCategory() {
        Category category = new Category("1", "Food", "Expenses for food");
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));

        Category foundCategory = getCategoryByIdUseCase.getCategoryById("1");

        assertNotNull(foundCategory);
        assertEquals("Food", foundCategory.getName());
    }

    @Test
    void getCategoryById_shouldThrowException_whenCategoryNotFound() {
        when(categoryRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> getCategoryByIdUseCase.getCategoryById("1"));

        String expectedMessage = "Category not found with id: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
