package com.mketsyrof.budget_tracker.repo;

import com.mketsyrof.budget_tracker.model.TransactionType;
import com.mketsyrof.budget_tracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCategory_Type(TransactionType transactionType);
}
