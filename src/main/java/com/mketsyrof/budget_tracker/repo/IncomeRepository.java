package com.mketsyrof.budget_tracker.repo;

import com.mketsyrof.budget_tracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
