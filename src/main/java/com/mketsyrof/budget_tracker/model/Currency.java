package com.mketsyrof.budget_tracker.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class Currency {
    @Id
    @Column(nullable = false)
    private String code;

    @Column
    private String name;

    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }

    protected Currency() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(code, currency.code) && Objects.equals(name, currency.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }
}
