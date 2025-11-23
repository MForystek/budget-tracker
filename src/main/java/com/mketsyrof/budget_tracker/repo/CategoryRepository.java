package com.mketsyrof.budget_tracker.repo;

import com.mketsyrof.budget_tracker.model.Category;
import com.mketsyrof.budget_tracker.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndType(String name, CategoryType type);
}
