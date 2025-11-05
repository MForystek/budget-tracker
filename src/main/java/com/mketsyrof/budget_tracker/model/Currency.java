package com.mketsyrof.budget_tracker.model;

import jakarta.persistence.*;

@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String code;

    @Column
    private String name;

    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }

    protected Currency() {
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
