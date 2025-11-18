package com.mketsyrof.budget_tracker.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public Category(String name, TransactionType type) {
        this.name = name;
        this.type = type;
    }

    protected Category() {
    }
}
