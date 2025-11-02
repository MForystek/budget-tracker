package com.mketsyrof.budget_tracker.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private Double amount;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    public Expense(LocalDate date, Double amount, PaymentType type, String description, ExpenseCategory category) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.category = category;
    }

    protected Expense() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }
}
