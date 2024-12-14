package com.costswatcher.costswatcher.public_expenses;

import com.costswatcher.costswatcher.user.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PublicExpensesController {
    private final PublicExpensesService publicExpensesService;

    public PublicExpensesController(PublicExpensesService publicExpensesService) {
        this.publicExpensesService = publicExpensesService;
    }

    @GetMapping("/public-expenses")
    public String getPublicExpenses(Model model) {
        if (UserEntity.signedInUser != null)
            return "redirect:/groups";
        model.addAttribute("expenses", publicExpensesService.getPublicExpenses());
        return "public_expenses";
    }

    @GetMapping("/submit-public-expense/{location}/{expense}")
    public String submitExpense(
        @PathVariable("location") String location,
        @PathVariable("expense") int expense,
        Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        publicExpensesService.submitTripExpense(location, expense);
        return "redirect:/groups";
    }
}