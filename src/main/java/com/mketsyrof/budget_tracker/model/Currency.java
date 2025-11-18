package com.mketsyrof.budget_tracker.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
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

}
