package com.mketsyrof.budget_tracker.business;

import com.mketsyrof.budget_tracker.model.*;
import com.mketsyrof.budget_tracker.repo.CategoryRepository;
import com.mketsyrof.budget_tracker.repo.CurrencyRepository;
import com.mketsyrof.budget_tracker.repo.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getAllIncomes() {
        return transactionRepository.findByCategory_Type(TransactionType.INCOME);
    }

    public List<Transaction> getAllExpenses() {
        return transactionRepository.findByCategory_Type(TransactionType.EXPENSE);
    }

    public void create(LocalDate date, Double amount, String currencyCode, PaymentMethod paymentMethod, Optional<String> description, String categoryName) throws IllegalArgumentException {
        Currency currency = currencyRepository.findByCode(currencyCode.toUpperCase(Locale.ROOT));
        if (currency == null) {
            throw new IllegalArgumentException("Unknown currency code: " + currencyCode);
        }
        Category category = categoryRepository.findByName(categoryName.toUpperCase(Locale.ROOT));
        if (category == null) {
            throw new IllegalArgumentException("Unknown category: " + categoryName);
        }
        transactionRepository.save(new Transaction(date, amount, currency, paymentMethod, description.orElse(""), category));
    }

    public void delete(long transactionId) throws NoSuchElementException {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("No transaction with id: " + transactionId));
        transactionRepository.delete(transaction);
    }
}
