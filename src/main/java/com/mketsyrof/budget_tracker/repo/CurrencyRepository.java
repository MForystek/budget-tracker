package com.mketsyrof.budget_tracker.repo;

import com.mketsyrof.budget_tracker.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCode(String code);
}
