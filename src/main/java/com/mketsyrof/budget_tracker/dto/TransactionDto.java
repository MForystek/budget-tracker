package com.mketsyrof.budget_tracker.dto;

import com.mketsyrof.budget_tracker.model.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Optional;

public class TransactionDto {
    private LocalDate date;

    @NotNull
    private Double amount;

    @Size(max = 3)
    private String currencyCode;

    @NotNull
    private PaymentMethod paymentMethod;

    private Optional<String> description;

    private String categoryName;

    public TransactionDto() {
    }

    public TransactionDto(LocalDate date, Double amount, String currencyCode, PaymentMethod paymentMethod, Optional<String> description, String categoryName) {
        this.date = date;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.categoryName = categoryName;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
