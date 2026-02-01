package com.mketsyrof.budget_tracker.business;

import com.mketsyrof.budget_tracker.model.Category;
import com.mketsyrof.budget_tracker.model.CategoryType;
import com.mketsyrof.budget_tracker.repo.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Transactional
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllSorted() {
        return categoryRepository.findAll(
                Sort.by("id").ascending()
        );
    }

    public List<Category> getAllByTypeSorted(CategoryType type) {
        return categoryRepository.findByType(
                type,
                Sort.by("id").ascending()
        );
    }

    public Category getByNameAndType(String name, CategoryType type) throws NoSuchElementException {
        String uppercaseName = name.toUpperCase(Locale.ROOT);
        return categoryRepository.findByNameAndType(uppercaseName, type)
                .orElseThrow(() -> new NoSuchElementException("No category " + uppercaseName + " of type " + type));
    }
}
