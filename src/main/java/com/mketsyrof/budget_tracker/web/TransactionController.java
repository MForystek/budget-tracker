package com.mketsyrof.budget_tracker.web;

import com.mketsyrof.budget_tracker.business.TransactionService;
import com.mketsyrof.budget_tracker.dto.TransactionDto;
import com.mketsyrof.budget_tracker.model.CategoryType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/api/transactions")
public class TransactionController {
    private static final String API_PATH_FOR_LOGS = "/api/transactions";

    private TransactionService transactionService;

    @GetMapping
    public List<TransactionDto> getAllTransactionOfGivenType(@RequestParam(name = "type", required = false) CategoryType type) {
        if (type == null) {
            log.info("GET " + API_PATH_FOR_LOGS);
            return transactionService.getAll();
        }

        log.info("GET " + API_PATH_FOR_LOGS + "?type={}", type);
        return transactionService.getAllOfType(type);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        log.info("POST " + API_PATH_FOR_LOGS);
        transactionService.create(transactionDto);
    }

    @PutMapping("/{transactionId}")
    public void updateTransaction(@PathVariable(value = "transactionId") long transactionId, @RequestBody @Valid TransactionDto transactionDto) {
        log.info("PUT " + API_PATH_FOR_LOGS + "/{}", transactionId);
        transactionService.update(transactionId, transactionDto);
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(@PathVariable(value = "transactionId") long transactionId) {
        log.info("DELETE " + API_PATH_FOR_LOGS + "/{}", transactionId);
        transactionService.delete(transactionId);
    }
}
