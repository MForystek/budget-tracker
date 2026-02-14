package com.mketsyrof.budget_tracker.unit;

import com.mketsyrof.budget_tracker.dto.TransactionDto;
import com.mketsyrof.budget_tracker.dto.TransactionMapper;
import com.mketsyrof.budget_tracker.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class TransactionMapperTest {
    @Test
    void toDtoTest() {
        Transaction transaction = new Transaction(
                LocalDate.EPOCH,
                1.0,
                new Currency("PLN", "Polish Złoty"),
                PaymentMethod.CARD,
                "Description",
                new Category("PAYCHECK", CategoryType.INCOME)
        );
        TransactionDto transactionDtoManual = new TransactionDto(
                null,
                LocalDate.EPOCH,
                1.0,
                "PLN",
                PaymentMethod.CARD,
                "Description",
                "PAYCHECK",
                CategoryType.INCOME
        );
        TransactionDto transactionDtoFromMapper = TransactionMapper.mapToDto(transaction);

        assertThat(transactionDtoManual).isEqualTo(transactionDtoFromMapper);
    }

    /**
     * Transaction here have id equal to 0 because it is managed by database and JPA
     * Test checks if all other fields are populated correctly.
     */
    @Test
    void toEntityTest() {
        Currency currency = new Currency("PLN", "Polish Złoty");
        Category category = new Category("PAYCHECK", CategoryType.INCOME);
        TransactionDto transactionDto = new TransactionDto(
                1L,
                LocalDate.EPOCH,
                1.0,
                currency.getCode(),
                PaymentMethod.CARD,
                "Description",
                category.getName(),
                category.getType()
        );
        Transaction transactionManual = new Transaction(
                LocalDate.EPOCH,
                1.0,
                currency,
                PaymentMethod.CARD,
                "Description",
                category
        );

        Transaction transactionFromMapper = TransactionMapper.mapToEntity(transactionDto, currency, category);

        assertThat(transactionManual).isEqualTo(transactionFromMapper);
    }
}
