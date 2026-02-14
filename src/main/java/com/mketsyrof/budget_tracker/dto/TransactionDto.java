package com.mketsyrof.budget_tracker.dto;

import com.mketsyrof.budget_tracker.model.PaymentMethod;
import com.mketsyrof.budget_tracker.model.CategoryType;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionDto {
    @NotNull
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    @Positive(message = "Amount must be positive")
    private Double amount;

    @Size(min = 3, max = 3)
    private String currencyCode;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotBlank
    private String description;

    @NotBlank
    private String categoryName;

    @NotNull
    private CategoryType categoryType;

    public TransactionDto(Long id, LocalDate date, Double amount, String currencyCode, PaymentMethod paymentMethod, String description, String categoryName, CategoryType categoryType) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

}
