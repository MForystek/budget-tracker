package com.mketsyrof.budget_tracker.unit;

import com.mketsyrof.budget_tracker.business.CategoryService;
import com.mketsyrof.budget_tracker.business.CurrencyService;
import com.mketsyrof.budget_tracker.business.TransactionService;
import com.mketsyrof.budget_tracker.dto.TransactionDto;
import com.mketsyrof.budget_tracker.dto.TransactionMapper;
import com.mketsyrof.budget_tracker.model.*;
import com.mketsyrof.budget_tracker.repo.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TransactionServiceTest {
    private static final long ID = 0;
    private static final Category CATEGORY_INCOME = new Category("PAYCHECK", TransactionType.INCOME);
    private static final Category CATEGORY_EXPENSE = new Category("GROCERIES", TransactionType.EXPENSE);
    private static final Currency CURRENCY = new Currency("PLN", "Polish Złoty");
    private static final Transaction TRANSACTION = new Transaction(LocalDate.EPOCH, 1.0, CURRENCY, PaymentMethod.CARD, "Description", CATEGORY_INCOME);

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @Mock
    private CategoryService categoryServiceMock;

    @Mock
    private CurrencyService currencyServiceMock;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void getByIdTest() {
        when(transactionRepositoryMock.findById(ID))
                .thenReturn(Optional.of(TRANSACTION));

        Transaction result = transactionService.getById(ID);

        assertThat(result).isEqualTo(TRANSACTION);
    }

    @Test
    void getAllTest() {
        TransactionDto transactionDto = TransactionMapper.mapToDto(TRANSACTION);

        when(transactionRepositoryMock.findAll()).thenReturn(List.of(TRANSACTION));

        List<TransactionDto> result = transactionService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(transactionDto);
    }

    @Test
    void getAllOfTypeIncomeTest() {
        when(transactionRepositoryMock.findByCategory_Type(TransactionType.INCOME))
                .thenReturn(List.of(TRANSACTION));

        List<TransactionDto> result = transactionService.getAllOfType(TransactionType.INCOME);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getCategoryName())
                .isEqualTo(CATEGORY_INCOME.getName());
    }

    @Test
    void getAllOfTypeExpenseTest() {
        Transaction transaction = new Transaction(LocalDate.EPOCH, 1.0, CURRENCY, PaymentMethod.CARD, "Description", CATEGORY_EXPENSE);

        when(transactionRepositoryMock.findByCategory_Type(TransactionType.EXPENSE))
                .thenReturn(List.of(transaction));

        List<TransactionDto> result = transactionService.getAllOfType(TransactionType.EXPENSE);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getCategoryName())
                .isEqualTo(CATEGORY_EXPENSE.getName());
    }

    @Test
    void createTest() {
        TransactionDto transactionDto = new TransactionDto(LocalDate.EPOCH, 1.0, CURRENCY.getCode(), PaymentMethod.CARD, "Description", CATEGORY_INCOME.getName(), CATEGORY_INCOME.getType());
        Transaction transaction = TransactionMapper.mapToEntity(transactionDto, CURRENCY, CATEGORY_INCOME);

        when(currencyServiceMock.getByCode(CURRENCY.getCode()))
                .thenReturn(CURRENCY);
        when(categoryServiceMock.getByNameAndType(CATEGORY_INCOME.getName(), CATEGORY_INCOME.getType()))
                .thenReturn(CATEGORY_INCOME);
        when(transactionRepositoryMock.save(any(Transaction.class)))
                .thenReturn(transaction);

        Transaction result = transactionService.create(transactionDto);

        verify(transactionRepositoryMock).save(argThat(t ->
                t.getDate().equals(LocalDate.EPOCH) &&
                t.getAmount() == 1.0 &&
                t.getCurrency().equals(CURRENCY) &&
                t.getPaymentMethod().equals(PaymentMethod.CARD) &&
                t.getDescription().equals("Description") &&
                t.getCategory().equals(CATEGORY_INCOME)));
        assertThat(result).isEqualTo(transaction);
    }
}
