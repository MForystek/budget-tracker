package com.mketsyrof.budget_tracker.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Transaction(LocalDate date, Double amount, Currency currency, PaymentMethod paymentMethod, String description, Category category) {
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.category = category;
    }

    protected Transaction() {
    }

}
