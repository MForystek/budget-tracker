package com.mketsyrof.budget_tracker.dto;

import com.mketsyrof.budget_tracker.model.Category;
import com.mketsyrof.budget_tracker.model.Currency;
import com.mketsyrof.budget_tracker.model.Transaction;

public class TransactionMapper {
    public static TransactionDto mapToDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getCurrency().getCode(),
                transaction.getPaymentMethod(),
                transaction.getDescription(),
                transaction.getCategory().getName(),
                transaction.getCategory().getType()
        );
    }

    public static Transaction mapToEntity(TransactionDto transactionDto, Currency currency, Category category) {
        return new Transaction(
                transactionDto.getDate(),
                transactionDto.getAmount(),
                currency,
                transactionDto.getPaymentMethod(),
                transactionDto.getDescription(),
                category
        );
    }
}
