package com.mketsyrof.budget_tracker.business;

import com.mketsyrof.budget_tracker.model.Currency;
import com.mketsyrof.budget_tracker.repo.CurrencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency getByCode(String code) throws NoSuchElementException {
        String uppercaseCode = code.toUpperCase(Locale.ROOT);
        return currencyRepository.findByCode(uppercaseCode)
                .orElseThrow(() -> new NoSuchElementException("No currency with code: " + uppercaseCode));
    }
}
