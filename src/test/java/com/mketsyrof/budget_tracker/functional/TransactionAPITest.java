package com.mketsyrof.budget_tracker.functional;

import com.mketsyrof.budget_tracker.dto.TransactionDto;
import com.mketsyrof.budget_tracker.dto.TransactionMapper;
import com.mketsyrof.budget_tracker.model.*;
import com.mketsyrof.budget_tracker.repo.CategoryRepository;
import com.mketsyrof.budget_tracker.repo.CurrencyRepository;
import com.mketsyrof.budget_tracker.repo.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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

    private Currency currency;
    private Category category;
    private TransactionDto transactionDto;
    private Transaction transaction;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void prepareVariables() {
        currency = getCurrency();
        category = getIncomeCategory();
        transactionDto = getTransactionDto(currency, category);
        transaction = TransactionMapper.mapToEntity(transactionDto, currency, category);
    }

    @AfterEach
    public void clearTransactionTable() {
        transactionRepository.deleteAll();
    }

    @Test
    public void apiGETAllTransactionsTest() {
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
    public void apiGETAllIncomeTransactionsTest() {
        apiGETAllTransactionsOfType(CategoryType.INCOME);
    }

    @Test
    public void apiGETAllExpenseTransactionsTest() {
        apiGETAllTransactionsOfType(CategoryType.EXPENSE);
    }

    private void apiGETAllTransactionsOfType(CategoryType type) {
        if (type.equals(CategoryType.EXPENSE)) {
            category = getExpenseCategory();
            transactionDto = getTransactionDto(currency, category);
            transaction = TransactionMapper.mapToEntity(transactionDto,currency, category);
        }

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

    @Test
    public void apiPOSTTransaction() {
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                TRANSACTION_API_URL,
                transactionDto,
                String.class
        );

        List<Transaction> dbTransactions = transactionRepository.findAll();

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(dbTransactions.size())
                .isEqualTo(1);
        assertThat(dbTransactions.getFirst().equalsNoId(transaction))
                .isTrue();

    }

    @Test
    public void apiPUTTransactionTest() {
        transactionDto = getTransactionDto(currency, getExpenseCategory());

        transactionRepository.save(transaction);
        long id = transactionRepository.findAll().getFirst().getId();

        ResponseEntity<String> response = testRestTemplate.exchange(
                TRANSACTION_API_URL + "/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(transactionDto),
                String.class
        );

        List<Transaction> dbTransactions = transactionRepository.findAll();

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(dbTransactions.size())
                .isEqualTo(1);
        assertThat(dbTransactions.getFirst().getCategory().getName())
                .isEqualTo(transactionDto.getCategoryName());
        assertThat(dbTransactions.getFirst().getCategory().getType())
                .isEqualTo(transactionDto.getCategoryType());
    }

    @Test
    public void apiDELETETransactionTest() {
        transactionRepository.save(transaction);
        long id = transactionRepository.findAll().getFirst().getId();

        testRestTemplate.delete(TRANSACTION_API_URL + "/" + id);

        List<Transaction> dbTransactions = transactionRepository.findAll();

        assertThat(dbTransactions.size())
                .isEqualTo(0);
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
                1L,
                LocalDate.EPOCH,
                1.0,
                currency.getCode(),
                PaymentMethod.CARD,
                "Description",
                category.getName(),
                category.getType());
    }
}
