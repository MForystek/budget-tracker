package com.mketsyrof.budget_tracker.unit;

import com.mketsyrof.budget_tracker.business.CategoryService;
import com.mketsyrof.budget_tracker.model.Category;
import com.mketsyrof.budget_tracker.model.Currency;
import com.mketsyrof.budget_tracker.model.TransactionType;
import com.mketsyrof.budget_tracker.repo.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private static final String NAME = "PAYCHECK";
    private static final TransactionType TYPE = TransactionType.INCOME;
    private static final Category CATEGORY = new Category(NAME, TYPE);
    private static final String NOT_UPPERCASE_NAME = "PaYCheCk";
    private static final String INCORRECT_NAME = "UPS";

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getByNameAndTypeTest() {
        when(categoryRepositoryMock.findByNameAndType(NAME, TYPE))
                .thenReturn(Optional.of(CATEGORY));

        Category result = categoryService.getByNameAndType(NAME, TYPE);

        assertThat(result).isEqualTo(CATEGORY);
    }

    @Test
    void getByNotUppercaseCodeTest() {
        when(categoryRepositoryMock.findByNameAndType(NAME, TYPE))
                .thenReturn(Optional.of(CATEGORY));

        Category result = categoryService.getByNameAndType(NOT_UPPERCASE_NAME, TYPE);

        assertThat(result).isEqualTo(CATEGORY);
    }

    @Test
    void getByIncorrectCodeTest() {
        when(categoryRepositoryMock.findByNameAndType(any(String.class), any(TransactionType.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getByNameAndType(INCORRECT_NAME, TYPE))
                .isInstanceOf(NoSuchElementException.class);
    }
}
