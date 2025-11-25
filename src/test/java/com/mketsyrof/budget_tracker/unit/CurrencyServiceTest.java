package com.mketsyrof.budget_tracker.unit;

import com.mketsyrof.budget_tracker.business.CurrencyService;
import com.mketsyrof.budget_tracker.model.Currency;
import com.mketsyrof.budget_tracker.repo.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CurrencyServiceTest {
    private static final String CODE = "PLN";
    private static final String NAME = "Polish Złoty";
    private static final Currency CURRENCY = new Currency(CODE, NAME);
    private static final String NOT_UPPERCASE_CODE = "pLN";
    private static final String INCORRECT_CODE = "UPS";

    @Mock
    private CurrencyRepository currencyRepositoryMock;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void getByCodeTest() {
        when(currencyRepositoryMock.findByCode(CODE))
                .thenReturn(Optional.of(CURRENCY));

        Currency result = currencyService.getByCode(CODE);

        assertThat(result).isEqualTo(CURRENCY);
    }

    @Test
    void getByNotUppercaseCodeTest() {
        when(currencyRepositoryMock.findByCode(CODE))
                .thenReturn(Optional.of(CURRENCY));

        Currency result = currencyService.getByCode(NOT_UPPERCASE_CODE);

        assertThat(result).isEqualTo(CURRENCY);
    }

    @Test
    void getByIncorrectCodeTest() {
        when(currencyRepositoryMock.findByCode(any(String.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> currencyService.getByCode(INCORRECT_CODE))
                .isInstanceOf(NoSuchElementException.class);
    }
}
