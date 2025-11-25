package com.mketsyrof.budget_tracker.functional;

import com.mketsyrof.budget_tracker.dto.TransactionDto;
import com.mketsyrof.budget_tracker.model.CategoryType;
import com.mketsyrof.budget_tracker.model.PaymentMethod;
import jakarta.transaction.Transactional;
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

@Transactional
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class TransactionAPITest {
    private static final String TRANSACTION_API_URL = "/api/transactions";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAllTransactionsTest() {
        TransactionDto transactionDto = new TransactionDto(
                LocalDate.of(2000, 1, 1),
                10.0,
                "PLN",
                PaymentMethod.CARD,
                "Income test 1",
                "PAYCHECK",
                CategoryType.INCOME);

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
                .isEqualTo(4);
        assertThat(response.getBody().getFirst())
                .isEqualTo(transactionDto);
    }

    @Test
    public void getAllIncomeTransactionsTest() {
        getAllTransactionsOfType("INCOME");
    }

    @Test
    public void getAllExpenseTransactionsTest() {
        getAllTransactionsOfType("EXPENSE");
    }

    private void getAllTransactionsOfType(String type) {
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
                .isEqualTo(2);
        assertThat(response.getBody()
                .stream()
                .map(TransactionDto::getCategoryType)
                .allMatch(t -> t.toString().equals(type)))
            .isTrue();
    }
}
