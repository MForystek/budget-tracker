package com.mketsyrof.budget_tracker.web;

import com.mketsyrof.budget_tracker.business.TransactionService;
import com.mketsyrof.budget_tracker.model.TransactionType;
import com.mketsyrof.budget_tracker.model.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "/api/transactions")
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAllTransactionOfGivenType(@RequestParam(name = "type", required = false) TransactionType type) {
        if (type == null) {
            return transactionService.getAllTransactions();
        }
        return type.equals(TransactionType.INCOME)
                ? transactionService.getAllIncomes()
                : transactionService.getAllExpenses();
    }
}
