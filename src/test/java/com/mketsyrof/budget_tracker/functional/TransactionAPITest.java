package com.mketsyrof.budget_tracker.functional;

import com.mketsyrof.budget_tracker.business.TransactionService;
import com.mketsyrof.budget_tracker.dto.TransactionDto;
import com.mketsyrof.budget_tracker.dto.TransactionMapper;
import com.mketsyrof.budget_tracker.model.*;
import com.mketsyrof.budget_tracker.repo.CategoryRepository;
import com.mketsyrof.budget_tracker.repo.CurrencyRepository;
import com.mketsyrof.budget_tracker.repo.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class TransactionAPITest {
    private static final String TRANSACTION_API_URL = "/api/transactions";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @AfterEach
    public void clearTransactionTable() {
        transactionRepository.deleteAll();
    }

    @Test
    public void getAllTransactionsTest() {
        Currency currency = getCurrency();
        Category category = getIncomeCategory();
        TransactionDto transactionDto = getTransactionDto(currency, category);
        Transaction transaction = TransactionMapper.mapToEntity(transactionDto, currency, category);

        transactionRepository.save(transaction);

        ResponseEntity<List<TransactionDto>> response = testRestTemplate.exchange(
                TRANSACTION_API_URL,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull();
        assertThat(response.getBody().size())
                .isEqualTo(1);
        assertThat(response.getBody().getFirst())
                .isEqualTo(transactionDto);
    }

    @Test
    public void getAllIncomeTransactionsTest() {
        getAllTransactionsOfType(CategoryType.INCOME);
    }

    @Test
    public void getAllExpenseTransactionsTest() {
        getAllTransactionsOfType(CategoryType.EXPENSE);
    }

    private Currency getCurrency() {
        return currencyRepository.findByCode("PLN").get();
    }

    private Category getIncomeCategory() {
        return categoryRepository.findByNameAndType("PAYCHECK", CategoryType.INCOME).get();
    }

    private Category getExpenseCategory() {
        return categoryRepository.findByNameAndType("GROCERIES", CategoryType.EXPENSE).get();
    }

    private TransactionDto getTransactionDto(Currency currency, Category category) {
        return new TransactionDto(
                LocalDate.EPOCH,
                1.0,
                currency.getCode(),
                PaymentMethod.CARD,
                "Description",
                category.getName(),
                category.getType());
    }

    private void getAllTransactionsOfType(CategoryType type) {
        Currency currency = getCurrency();
        Category category = type.equals(CategoryType.INCOME) ? getIncomeCategory() : getExpenseCategory();
        TransactionDto transactionDto = getTransactionDto(currency, category);
        Transaction transaction = TransactionMapper.mapToEntity(transactionDto, currency, category);

        transactionRepository.save(transaction);

        ResponseEntity<List<TransactionDto>> response = testRestTemplate.exchange(
                TRANSACTION_API_URL + "?type={type}",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {},
                Map.of("type", type));

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull();
        assertThat(response.getBody().size())
                .isEqualTo(1);
        assertThat(response.getBody()
                .stream()
                .map(TransactionDto::getCategoryType)
                .allMatch(t -> t.equals(type)))
            .isTrue();
    }
}
