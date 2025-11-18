package com.mketsyrof.budget_tracker.web;

import com.mketsyrof.budget_tracker.business.TransactionService;
import com.mketsyrof.budget_tracker.dto.TransactionDto;
import com.mketsyrof.budget_tracker.model.TransactionType;
import com.mketsyrof.budget_tracker.model.Transaction;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "/api/transactions")
public class TransactionController {
    private static final String API_PATH_FOR_LOGS = "/api/transactions";

    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactionOfGivenType(@RequestParam(name = "type", required = false) TransactionType type) {
        if (type.equals(TransactionType.INCOME)) {
            log.info("GET " + API_PATH_FOR_LOGS + "?type=INCOME");
            return transactionService.getAllIncomes();
        }
        if (type.equals(TransactionType.EXPENSE)) {
            log.info("GET " + API_PATH_FOR_LOGS + "?type=EXPENSE");
            return transactionService.getAllExpenses();
        }
        log.info("GET " + API_PATH_FOR_LOGS);
        return transactionService.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        log.info("POST " + API_PATH_FOR_LOGS);
        transactionService.create(
                transactionDto.getDate(),
                transactionDto.getAmount(),
                transactionDto.getCurrencyCode(),
                transactionDto.getPaymentMethod(),
                transactionDto.getDescription(),
                transactionDto.getCategoryName());
    }

    @DeleteMapping("{transactionId}")
    public void deleteTransaction(@PathVariable(value = "transactionId") long transactionId) {
        log.info("DELETE " + API_PATH_FOR_LOGS + "/{}", transactionId);
        transactionService.delete(transactionId);
    }
}
