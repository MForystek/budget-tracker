package com.mketsyrof.budget_tracker.dto;

import com.mketsyrof.budget_tracker.model.Category;
import com.mketsyrof.budget_tracker.model.Currency;
import com.mketsyrof.budget_tracker.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionMapper {
    public TransactionDto mapToDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getCurrency().getCode(),
                transaction.getPaymentMethod(),
                Optional.of(transaction.getDescription()),
                transaction.getCategory().getName()
        );
    }

    public Transaction mapToEntity(TransactionDto transactionDto, Currency currency, Category category) {
        return new Transaction(
                transactionDto.getDate(),
                transactionDto.getAmount(),
                currency,
                transactionDto.getPaymentMethod(),
                transactionDto.getDescription().orElse(""),
                category
        );
    }
}
