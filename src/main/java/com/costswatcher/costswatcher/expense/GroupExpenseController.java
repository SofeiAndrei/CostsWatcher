package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.expense.helper.AddExpenseParticipantRequest;
import com.costswatcher.costswatcher.expense.helper.AddGroupExpenseRequest;
import com.costswatcher.costswatcher.expense.helper.GroupExpenseParticipantId;
import com.costswatcher.costswatcher.user.UserEntity;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class GroupExpenseController {
    private final GroupExpenseService groupExpenseService;

    public GroupExpenseController(GroupExpenseService groupExpenseService) {
        this.groupExpenseService = groupExpenseService;
    }

    @GetMapping("/group/{idGroup}/delete/group-expense/{idExpense}")
    public String deleteGroupExpense(
            @PathVariable("idGroup") int idGroup,
            @PathVariable("idExpense") int idExpense,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        groupExpenseService.deleteGroupExpense(idExpense, idGroup);
        return "redirect:/group/edit/" + idGroup + "/group-expenses";
    }

    @GetMapping("/group/{idGroup}/show/group-expense/{idExpense}")
    public String showGroupExpense(
            @PathVariable("idGroup") int idGroup,
            @PathVariable("idExpense") int idExpense,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupId", idGroup);
        model.addAttribute("expenseId", idExpense);
        model.addAttribute("expenseEntity", groupExpenseService.getExpenseObjByIdExpense(idExpense).orElse(new GroupExpenseEntity()));
        model.addAttribute("participants", groupExpenseService.getExpenseParticipants(idExpense));
        return "show_group_expense";
    }

    @GetMapping("/group/{idGroup}/edit/group-expense/{idExpense}")
    public String editGroupExpense(
            @PathVariable("idGroup") int idGroup,
            @PathVariable("idExpense") int idExpense,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupId", idGroup);
        model.addAttribute("expenseId", idExpense);
        model.addAttribute("expenseEntity", groupExpenseService.getExpenseObjByIdExpense(idExpense).orElse(new GroupExpenseEntity()));
        model.addAttribute("participants", groupExpenseService.getExpenseParticipants(idExpense));
        return "edit_group_expense";
    }

    @PostMapping("/group/{idGroup}/edit/group-expense/{idExpense}/submit")
    public String submitEditedGroupExpense(
            @PathVariable("idGroup") int idGroup,
            @PathVariable("idExpense") int idExpense,
            @ModelAttribute("expenseEntity") GroupExpenseEntity formExpense,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";

        String invalidMessage = null;
        if (formExpense.getName().length() < 3)
            invalidMessage = "Name must have at least 3 characters!";
        else if (formExpense.getName().length() > 40)
            invalidMessage = "Name must have at most 40 characters!";
        if (invalidMessage != null) {
            model.addAttribute("groupId", idGroup);
            model.addAttribute("expenseId", idExpense);
            model.addAttribute("expenseEntity", groupExpenseService.getExpenseObjByIdExpense(idExpense).orElse(new GroupExpenseEntity()));
            model.addAttribute("participants", groupExpenseService.getExpenseParticipants(idExpense));
            model.addAttribute("invalidFormData", invalidMessage);
            return "edit_group_expense";
        }

        groupExpenseService.saveEditedExpenseName(idExpense, formExpense.getName());
        return "redirect:/group/edit/" + idGroup + "/group-expenses";
    }

    @GetMapping("/group/{idGroup}/edit/group-expense/{idExpense}/delete-member/{idUser}")
    public String deleteExpenseParticipant(
            @PathVariable("idGroup") int idGroup,
            @PathVariable("idExpense") int idExpense,
            @PathVariable("idUser") int idUser,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        if (groupExpenseService.getExpenseParticipants(idExpense).size() > 1)
            groupExpenseService.deleteExpenseParticipant(new GroupExpenseParticipantId(idUser, idExpense));
        return "redirect:/group/"+ idGroup + "/edit/group-expense/" + idExpense;
    }

    @GetMapping("/group/{idGroup}/edit/group-expense/{idExpense}/add-member")
    public String addExpenseParticipant(
            @PathVariable("idGroup") int idGroup,
            @PathVariable("idExpense") int idExpense,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupId", idGroup);
        model.addAttribute("expenseId", idExpense);
        model.addAttribute("formData", new AddExpenseParticipantRequest());
        return "add_group_expense_participant";
    }

    @PostMapping("/group/{idGroup}/edit/group-expense/{idExpense}/add-member/submit")
    public String submitNewExpenseParticipant(
            @PathVariable("idGroup") int idGroup,
            @PathVariable("idExpense") int idExpense,
            @ModelAttribute("formData") AddExpenseParticipantRequest formData,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        String username = formData.getUsername();
        int amount = formData.getAmount();
        String invalidMessage = null;
        if (username.length() < 3)
            invalidMessage = "Name must have at least 3 characters!";
        else if (username.length() > 40)
            invalidMessage = "Name must have at most 40 characters!";
        else if (amount < 1)
            invalidMessage = "The amount must be at least 1!";
        else
            invalidMessage = groupExpenseService.addExpenseParticipant(idExpense, username, amount);

        if (invalidMessage != null) {
            model.addAttribute("invalidFormData", invalidMessage);
            model.addAttribute("groupId", idGroup);
            model.addAttribute("expenseId", idExpense);
            model.addAttribute("formData", formData);
            return "add_group_expense_participant";
        }
        return "redirect:/group/" + idGroup + "/edit/group-expense/" + idExpense;
    }

    @GetMapping("/group/{idGroup}/add/group-expense")
    public String addGroupExpense(
            @PathVariable("idGroup") int idGroup,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupId", idGroup);
        model.addAttribute("formData", new AddGroupExpenseRequest());
        return "add_group_expense";
    }

    @PostMapping("/group/{idGroup}/add/group-expense/submit")
    public String submitNewGroupExpense(
            @ModelAttribute("formData") AddGroupExpenseRequest formData,
            @PathVariable("idGroup") int idGroup,
            Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";

        String invalidMessage;
        if (formData.getExpenseName().length() < 3 || formData.getExpenseName().length() > 40)
            invalidMessage = "The expense name must have between 3-40 characters!";
        else if (formData.getUsername1().length() < 3 || formData.getUsername1().length() > 40 ||
                 formData.getUsername2().length() < 3 || formData.getUsername2().length() > 40)
            invalidMessage = "The members' username must have between 3-40 characters!";
        else if (Objects.equals(formData.getUsername1(), formData.getUsername2()))
            invalidMessage = "The same member can't be added twice!";
        else if (formData.getAmount1() < 1 || formData.getAmount2() < 1)
            invalidMessage = "The members' amount must be at least 1!";
        else {
            List<Pair<String, Integer>> participants = new ArrayList<>();
            participants.add(Pair.of(formData.getUsername1(), formData.getAmount1()));
            participants.add(Pair.of(formData.getUsername2(), formData.getAmount2()));
            invalidMessage = groupExpenseService.addGroupExpense(formData.getExpenseName(), idGroup, participants);
        }

        if (invalidMessage != null) {
            model.addAttribute("groupId", idGroup);
            model.addAttribute("formData", formData);
            model.addAttribute("invalidFormData", invalidMessage);
            return "add_group_expense";
        }
        return "redirect:/group/edit/" + idGroup + "/group-expenses";
    }
}
