package com.mketsyrof.budget_tracker.business;

import com.mketsyrof.budget_tracker.model.Category;
import com.mketsyrof.budget_tracker.model.TransactionType;
import com.mketsyrof.budget_tracker.repo.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category getByNameAndType(String name, TransactionType type) throws NoSuchElementException {
        String uppercaseName = name.toUpperCase(Locale.ROOT);
        return categoryRepository.findByNameAndType(uppercaseName, type)
                .orElseThrow(() -> new NoSuchElementException("No category " + uppercaseName + " of type " + type));
    }
}
