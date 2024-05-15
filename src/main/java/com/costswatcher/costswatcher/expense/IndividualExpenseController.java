package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.user.UserEntity;
import com.costswatcher.costswatcher.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndividualExpenseController {
    private final IndividualExpenseService individualExpenseService;
    private final UserService userService;

    @Autowired
    public IndividualExpenseController(IndividualExpenseService individualExpenseService, UserService userService) {
        this.individualExpenseService = individualExpenseService;
        this.userService = userService;
    }

    @GetMapping("/group/{groupId}/show/individual-expense/{expenseId}")
    public String showIndividualExpense(@PathVariable("groupId") int groupId, @PathVariable("expenseId") int expenseId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupId", groupId);
        IndividualExpenseEntity expenseEntity = individualExpenseService.getByIdExpense(expenseId);
        model.addAttribute("expenseEntity", expenseEntity);
        UserEntity user = userService.getUserByIdUser(expenseEntity.getIdUser()).orElse(null);
        model.addAttribute("username", user != null ? user.getUsername() : "");
        return "show_individual_expense";
    }

    @GetMapping("/group/{groupId}/delete/individual-expense/{expenseId}")
    public String deleteIndividualExpense(@PathVariable("groupId") int groupId, @PathVariable("expenseId") int expenseId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        individualExpenseService.deleteIndividualExpense(expenseId, groupId);
        return "redirect:/group/edit/" + groupId + "/individual-expenses";
    }

    @GetMapping("/group/{groupId}/edit/individual-expense/{expenseId}")
    public String editIndividualExpense(@PathVariable("groupId") int groupId, @PathVariable("expenseId") int expenseId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupId", groupId);
        IndividualExpenseEntity expenseEntity = individualExpenseService.getByIdExpense(expenseId);
        model.addAttribute("expenseEntity", expenseEntity);
        UserEntity user = userService.getUserByIdUser(expenseEntity.getIdUser()).orElse(null);
        model.addAttribute("username", user != null ? user.getUsername() : "");
        return "edit_individual_expense";
    }

    @PostMapping("/group/{groupId}/edit/individual-expense/{expenseId}/submit")
    public String submitEditedIndividualExpense(
            @PathVariable("groupId") int groupId,
            @PathVariable("expenseId") int expenseId,
            @ModelAttribute IndividualExpenseEntity formExpense,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupId", groupId);
        model.addAttribute("expenseEntity", formExpense);
        UserEntity user = userService.getUserByIdUser(formExpense.getIdUser()).orElse(null);
        model.addAttribute("username", user != null ? user.getUsername() : "");
        if (formExpense.getAmount() < 1)
            model.addAttribute("invalidFormData", "Amount must be at least 1!");
        else if (formExpense.getName().length() < 3)
            model.addAttribute("invalidFormData", "Name must have at least 3 characters!");
        else if (formExpense.getName().length() > 40)
            model.addAttribute("invalidFormData", "Name must have at most 40 characters!");
        else {
            IndividualExpenseEntity expenseToUpdate = individualExpenseService.getByIdExpense(expenseId);
            expenseToUpdate.setName(formExpense.getName());
            expenseToUpdate.setAmount(formExpense.getAmount());
            individualExpenseService.updateIndividualExpense(expenseToUpdate);
            return "redirect:/group/edit/" + groupId + "/individual-expenses";
        }
        return "edit_individual_expense";
    }

    @GetMapping("/group/{groupId}/add/individual-expense")
    public String addIndividualExpense(@PathVariable("groupId") int groupId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupId", groupId);
        model.addAttribute("expenseEntity", new IndividualExpenseEntity());
        return "add_individual_expense";
    }

    @PostMapping("/group/{groupId}/add/individual-expense/submit")
    public String submitNewIndividualExpense(
            @PathVariable("groupId") int groupId,
            @ModelAttribute("expenseEntity") IndividualExpenseEntity formExpense,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupId", groupId);
        model.addAttribute("expenseEntity", formExpense);
        if (formExpense.getAmount() < 1)
            model.addAttribute("invalidFormData", "Amount must be at least 1!");
        else if (formExpense.getName().length() < 3)
            model.addAttribute("invalidFormData", "Name must have at least 3 characters!");
        else if (formExpense.getName().length() > 40)
            model.addAttribute("invalidFormData", "Name must have at most 40 characters!");
        else {
            formExpense.setIdGroup(groupId);
            IndividualExpenseEntity newExpense = individualExpenseService.addIndividualExpense(formExpense);
            if (newExpense != null)
                return "redirect:/group/edit/" + groupId + "/individual-expenses";
            model.addAttribute("invalidFormData", "Oops! The expense creation failed.");
        }
        return "add_individual_expense";
    }
}
