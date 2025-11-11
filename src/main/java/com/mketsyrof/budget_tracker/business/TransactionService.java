package com.mketsyrof.budget_tracker.business;

import com.mketsyrof.budget_tracker.model.*;
import com.mketsyrof.budget_tracker.repo.CategoryRepository;
import com.mketsyrof.budget_tracker.repo.CurrencyRepository;
import com.mketsyrof.budget_tracker.repo.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    public void createTransaction(LocalDate date, Double amount, String currencyCode, PaymentMethod paymentMethod, Optional<String> description, String categoryName) {
        Currency currency = currencyRepository.findByCode(currencyCode);
        if (currency == null) {
            throw new IllegalArgumentException("Unknown currency code: " + currencyCode);
        }

        Category category = categoryRepository.findByName(categoryName);
        transactionRepository.save(new Transaction(date, amount, currency, paymentMethod, description.orElse(""), category));
    }
}
