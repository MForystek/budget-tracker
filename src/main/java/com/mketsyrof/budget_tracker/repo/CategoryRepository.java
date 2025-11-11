package com.mketsyrof.budget_tracker.repo;

import com.mketsyrof.budget_tracker.model.Category;
import com.mketsyrof.budget_tracker.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
