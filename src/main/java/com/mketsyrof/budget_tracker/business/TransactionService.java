package com.mketsyrof.budget_tracker.business;

import com.mketsyrof.budget_tracker.dto.TransactionDto;
import com.mketsyrof.budget_tracker.dto.TransactionMapper;
import com.mketsyrof.budget_tracker.model.*;
import com.mketsyrof.budget_tracker.repo.CategoryRepository;
import com.mketsyrof.budget_tracker.repo.CurrencyRepository;
import com.mketsyrof.budget_tracker.repo.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<TransactionDto> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::mapToDto)
                .toList();
    }

    public List<TransactionDto> getAllOfType(TransactionType type) {
        return transactionRepository.findByCategory_Type(type)
                .stream()
                .map(transactionMapper::mapToDto)
                .toList();
    }

    public Transaction create(TransactionDto transactionDto) throws IllegalArgumentException {
        Currency currency = currencyRepository.findByCode(transactionDto.getCurrencyCode().toUpperCase(Locale.ROOT));
        if (currency == null) {
            throw new IllegalArgumentException("Unknown currency code: " + transactionDto.getCurrencyCode());
        }
        Category category = categoryRepository.findByName(transactionDto.getCategoryName().toUpperCase(Locale.ROOT));
        if (category == null) {
            throw new IllegalArgumentException("Unknown category: " + transactionDto.getCategoryName());
        }
        return transactionRepository.save(transactionMapper.mapToEntity(transactionDto, currency, category));
    }

    public Transaction update(long transactionId, TransactionDto transactionDto) throws NoSuchElementException {
        Transaction transaction = verifyAndGetTransactionById(transactionId);
        transaction.setDate(transactionDto.getDate());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setCurrency(currencyRepository.findByCode(transactionDto.getCurrencyCode()));
        transaction.setPaymentMethod(transactionDto.getPaymentMethod());
        if (transactionDto.getDescription().isPresent()) {
            transaction.setDescription(transactionDto.getDescription().get());
        }
        transaction.setCategory(categoryRepository.findByName(transactionDto.getCategoryName()));
        return transactionRepository.save(transaction);
    }

    public void delete(long transactionId) throws NoSuchElementException {
        Transaction transaction = verifyAndGetTransactionById(transactionId);
        transactionRepository.delete(transaction);
    }

    private Transaction verifyAndGetTransactionById(long transactionId) throws NoSuchElementException {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("No transaction with id: " + transactionId));
    }
}
