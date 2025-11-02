package com.mketsyrof.budget_tracker.business;

import com.mketsyrof.budget_tracker.model.TransactionType;
import com.mketsyrof.budget_tracker.model.Transaction;
import com.mketsyrof.budget_tracker.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getAllIncomes() {
        return transactionRepository.findByCategory_Type(TransactionType.INCOME);
    }

    public List<Transaction> getAllExpenses() {
        return transactionRepository.findByCategory_Type(TransactionType.EXPENSE);
    }
}
