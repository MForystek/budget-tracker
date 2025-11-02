package com.mketsyrof.budget_tracker.repo;

import com.mketsyrof.budget_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
