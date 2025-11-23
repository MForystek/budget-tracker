package com.mketsyrof.budget_tracker.dto;

import com.mketsyrof.budget_tracker.model.PaymentMethod;
import com.mketsyrof.budget_tracker.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionDto {
    @NotNull
    private LocalDate date;

    @NotNull
    private Double amount;

    @Size(max = 3)
    private String currencyCode;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private String description;

    @NotNull
    private String categoryName;

    @NotNull
    private TransactionType transactionType;

    public TransactionDto(LocalDate date, Double amount, String currencyCode, PaymentMethod paymentMethod, String description, String categoryName, TransactionType transactionType) {
        this.date = date;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.categoryName = categoryName;
        this.transactionType = transactionType;
    }

}
