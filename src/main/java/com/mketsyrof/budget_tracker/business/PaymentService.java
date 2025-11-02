package com.mketsyrof.budget_tracker.business;

import com.mketsyrof.budget_tracker.model.Expense;
import com.mketsyrof.budget_tracker.model.Income;
import com.mketsyrof.budget_tracker.repo.ExpenseRepository;
import com.mketsyrof.budget_tracker.repo.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public PaymentService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}
