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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TransactionServiceTest {
    private static final long ID = 1;
    private static final LocalDate DATE = LocalDate.EPOCH;
    private static final double AMOUNT = 1.0;
    private static final Currency CURRENCY = new Currency("PLN", "Polish Złoty");
    private static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CARD;
    private static final String DESCRIPTION = "Description";
    private static final Category CATEGORY_INCOME = new Category("PAYCHECK", CategoryType.INCOME);
    private static final Category CATEGORY_EXPENSE = new Category("GROCERIES", CategoryType.EXPENSE);
    private static final Transaction TRANSACTION = new Transaction(DATE, AMOUNT, CURRENCY, PAYMENT_METHOD, DESCRIPTION, CATEGORY_INCOME);
    private static final TransactionDto TRANSACTION_DTO = new TransactionDto(ID, DATE, AMOUNT, CURRENCY.getCode(), PAYMENT_METHOD, DESCRIPTION, CATEGORY_INCOME.getName(), CATEGORY_INCOME.getType());

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
    void getByIdTransactionNotFoundTest() {
        when(transactionRepositoryMock.findById(ID))
                .thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> transactionService.getById(ID))
                .isInstanceOf(NoSuchElementException.class);
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
        when(transactionRepositoryMock.findByCategory_Type(CategoryType.INCOME))
                .thenReturn(List.of(TRANSACTION));

        List<TransactionDto> result = transactionService.getAllOfType(CategoryType.INCOME);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getCategoryName())
                .isEqualTo(CATEGORY_INCOME.getName());
    }

    @Test
    void getAllOfTypeExpenseTest() {
        Transaction transaction = new Transaction(DATE, AMOUNT, CURRENCY, PAYMENT_METHOD, DESCRIPTION, CATEGORY_EXPENSE);

        when(transactionRepositoryMock.findByCategory_Type(CategoryType.EXPENSE))
                .thenReturn(List.of(transaction));

        List<TransactionDto> result = transactionService.getAllOfType(CategoryType.EXPENSE);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getCategoryName())
                .isEqualTo(CATEGORY_EXPENSE.getName());
    }

    @Test
    void createTest() {
        Transaction transaction = TransactionMapper.mapToEntity(TRANSACTION_DTO, CURRENCY, CATEGORY_INCOME);

        when(currencyServiceMock.getByCode(CURRENCY.getCode()))
                .thenReturn(CURRENCY);
        when(categoryServiceMock.getByNameAndType(CATEGORY_INCOME.getName(), CATEGORY_INCOME.getType()))
                .thenReturn(CATEGORY_INCOME);
        when(transactionRepositoryMock.save(any(Transaction.class)))
                .thenReturn(transaction);

        Transaction result = transactionService.create(TRANSACTION_DTO);

        verify(currencyServiceMock).getByCode(CURRENCY.getCode());
        verify(categoryServiceMock).getByNameAndType(CATEGORY_INCOME.getName(), CATEGORY_INCOME.getType());

        verify(transactionRepositoryMock).save(argThat(t ->
                t.getDate().equals(DATE) &&
                t.getAmount() == AMOUNT &&
                t.getCurrency().equals(CURRENCY) &&
                t.getPaymentMethod().equals(PaymentMethod.CARD) &&
                t.getDescription().equals(DESCRIPTION) &&
                t.getCategory().equals(CATEGORY_INCOME)));

        assertThat(result).isEqualTo(transaction);
    }

    @Test
    void createMissingCurrencyTest() {
        when(currencyServiceMock.getByCode(CURRENCY.getCode()))
                .thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> transactionService.create(TRANSACTION_DTO))
                .isInstanceOf(NoSuchElementException.class);

        verifyNoInteractions(transactionRepositoryMock);
    }

    @Test
    void createMissingCategoryTest() {
        when(categoryServiceMock.getByNameAndType(CATEGORY_INCOME.getName(), CATEGORY_INCOME.getType()))
                .thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> transactionService.create(TRANSACTION_DTO))
                .isInstanceOf(NoSuchElementException.class);

        verifyNoInteractions(transactionRepositoryMock);
    }

    @Test
    void updateTest() {
        Transaction transaction = new Transaction(DATE, 5.0, CURRENCY, PAYMENT_METHOD, DESCRIPTION, CATEGORY_INCOME);
        Transaction newTransaction = TransactionMapper.mapToEntity(TRANSACTION_DTO, CURRENCY, CATEGORY_INCOME);

        when(currencyServiceMock.getByCode(CURRENCY.getCode()))
                .thenReturn(CURRENCY);
        when(categoryServiceMock.getByNameAndType(CATEGORY_INCOME.getName(), CATEGORY_INCOME.getType()))
                .thenReturn(CATEGORY_INCOME);
        when(transactionRepositoryMock.findById(ID))
                .thenReturn(Optional.of(transaction));
        when(transactionRepositoryMock.save(newTransaction))
                .thenReturn(newTransaction);

        Transaction result = transactionService.update(ID, TRANSACTION_DTO);

        verify(currencyServiceMock).getByCode(CURRENCY.getCode());
        verify(categoryServiceMock).getByNameAndType(CATEGORY_INCOME.getName(), CATEGORY_INCOME.getType());

        assertThat(result).isEqualTo(newTransaction);

        assertThat(TRANSACTION_DTO.getDate()).isEqualTo(transaction.getDate());
        assertThat(TRANSACTION_DTO.getCurrencyCode()).isEqualTo(transaction.getCurrency().getCode());
        assertThat(TRANSACTION_DTO.getPaymentMethod()).isEqualTo(transaction.getPaymentMethod());
        assertThat(TRANSACTION_DTO.getDescription()).isEqualTo(transaction.getDescription());
        assertThat(TRANSACTION_DTO.getCategoryName()).isEqualTo(transaction.getCategory().getName());
        assertThat(TRANSACTION_DTO.getCategoryType()).isEqualTo(transaction.getCategory().getType());

        verify(transactionRepositoryMock).save(newTransaction);
    }

    @Test
    void updateMissingCurrencyTest() {
        when(currencyServiceMock.getByCode(CURRENCY.getCode()))
                .thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> transactionService.update(ID, TRANSACTION_DTO))
                .isInstanceOf(NoSuchElementException.class);

        verifyNoInteractions(transactionRepositoryMock);
    }

    @Test
    void updateMissingCategoryTest() {
        when(categoryServiceMock.getByNameAndType(CATEGORY_INCOME.getName(), CATEGORY_INCOME.getType()))
                .thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> transactionService.update(ID, TRANSACTION_DTO))
                .isInstanceOf(NoSuchElementException.class);

        verifyNoInteractions(transactionRepositoryMock);
    }

    @Test
    void updateTransactionNotFound() {
        when(transactionRepositoryMock.findById(ID))
                .thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> transactionService.update(ID, TRANSACTION_DTO))
                .isInstanceOf(NoSuchElementException.class);
    }
}
