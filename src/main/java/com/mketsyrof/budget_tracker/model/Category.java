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
    private CategoryType type;

    public Category(String name, CategoryType type) {
        this.name = name;
        this.type = type;
    }

    protected Category() {
    }
}
