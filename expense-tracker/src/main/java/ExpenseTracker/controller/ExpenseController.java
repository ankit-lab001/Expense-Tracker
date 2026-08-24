package ExpenseTracker.controller;

import ExpenseTracker.model.Expense;
import ExpenseTracker.repository.ExpenseRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Home page
    @GetMapping("/")
    public String home(Model model) {

        var expenses = expenseRepository.findAll();

        double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        model.addAttribute("total", total);
        model.addAttribute("count", expenses.size());

        return "index";
    }

    // Add expense page
    @GetMapping("/add-expense")
    public String addExpenseForm(Model model) {

        model.addAttribute("expense", new Expense());

        return "add-expense";
    }

    // Save expense
    @PostMapping("/save-expense")
    public String saveExpense(@ModelAttribute Expense expense) {

        expenseRepository.save(expense);

        return "redirect:/expenses";
    }

    // View all expenses
    @GetMapping("/expenses")
    public String viewExpenses(Model model) {

        var expenses = expenseRepository.findAll();

        double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        model.addAttribute("expenses", expenses);
        model.addAttribute("total", total);

        return "expenses";
    }

    // Delete expense
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {

        expenseRepository.deleteById(id);

        return "redirect:/expenses";
    }

    // Edit expense page
    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id, Model model) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid expense ID"));

        model.addAttribute("expense", expense);

        return "add-expense";
    }

    // Update expense
    @PostMapping("/update-expense")
    public String updateExpense(@ModelAttribute Expense expense) {

        expenseRepository.save(expense);

        return "redirect:/expenses";
    }
}