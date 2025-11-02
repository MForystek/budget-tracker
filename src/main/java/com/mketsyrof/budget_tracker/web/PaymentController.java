package com.mketsyrof.budget_tracker.web;

import com.mketsyrof.budget_tracker.business.PaymentService;
import com.mketsyrof.budget_tracker.model.Expense;
import com.mketsyrof.budget_tracker.model.Income;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(path = "/incomes")
    public List<Income> getAllIncomes() {
        return paymentService.getAllIncomes();
    }

    @GetMapping(path = "/expenses")
    public List<Expense> getAllExpenses() {
        return paymentService.getAllExpenses();
    }
}
