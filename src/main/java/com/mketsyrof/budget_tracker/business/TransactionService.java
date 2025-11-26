package com.mketsyrof.budget_tracker.business;

import com.mketsyrof.budget_tracker.dto.TransactionDto;
import com.mketsyrof.budget_tracker.dto.TransactionMapper;
import com.mketsyrof.budget_tracker.model.*;
import com.mketsyrof.budget_tracker.repo.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Transactional
@Service
public class TransactionService {
    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction getById(long transactionId) throws NoSuchElementException {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("No transaction with id " + transactionId));
    }

    public List<TransactionDto> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionMapper::mapToDto)
                .toList();
    }

    public List<TransactionDto> getAllOfType(CategoryType type) {
        return transactionRepository.findByCategory_Type(type)
                .stream()
                .map(TransactionMapper::mapToDto)
                .toList();
    }

    public Transaction create(TransactionDto transactionDto) throws NoSuchElementException {
        Currency currency = currencyService.getByCode(transactionDto.getCurrencyCode());
        Category category = categoryService.getByNameAndType(transactionDto.getCategoryName(), transactionDto.getCategoryType());
        return transactionRepository.save(TransactionMapper.mapToEntity(transactionDto, currency, category));
    }

    public Transaction update(long transactionId, TransactionDto transactionDto) throws NoSuchElementException {
        Currency currency = currencyService.getByCode(transactionDto.getCurrencyCode());
        Category category = categoryService.getByNameAndType(transactionDto.getCategoryName(), transactionDto.getCategoryType());

        Transaction transaction = getById(transactionId);
        transaction.setDate(transactionDto.getDate());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setCurrency(currency);
        transaction.setPaymentMethod(transactionDto.getPaymentMethod());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setCategory(category);
        return transactionRepository.save(transaction);
    }

    public void delete(long transactionId) throws NoSuchElementException {
        transactionRepository.deleteById(transactionId);
    }
}
