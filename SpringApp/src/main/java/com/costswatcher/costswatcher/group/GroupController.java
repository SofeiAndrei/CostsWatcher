package com.costswatcher.costswatcher.group;

import com.costswatcher.costswatcher.expense.GroupExpenseService;
import com.costswatcher.costswatcher.expense.IndividualExpenseService;
import com.costswatcher.costswatcher.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class GroupController {
    private final GroupService groupService;
    private final IndividualExpenseService individualExpenseService;
    private final GroupExpenseService groupExpenseService;

    @Autowired
    public GroupController(GroupService groupService, IndividualExpenseService individualExpenseService, GroupExpenseService groupExpenseService) {
        this.groupService = groupService;
        this.individualExpenseService = individualExpenseService;
        this.groupExpenseService = groupExpenseService;
    }

    @GetMapping("/groups")
    public String getGroupsForMember(
        @RequestParam(name = "page", defaultValue = "1") int pageNumber,
        Model model
    ) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        Page<GroupEntity> groupsCollection = groupService.findAllForUser(UserEntity.signedInUser.getIdUser(), pageNumber - 1);
        model.addAttribute("signedInUser", UserEntity.signedInUser);
        model.addAttribute("groupsCollection", groupsCollection);
        model.addAttribute("pagesCount", groupsCollection.getTotalPages());
        model.addAttribute("prevPageNumber", pageNumber - 1);
        model.addAttribute("nextPageNumber", pageNumber + 1);
        return "groups_list";
    }

    @GetMapping("/group/create")
    public String createGroupPage(Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupEntity", new GroupEntity());
        return "create_group_form";
    }

    @PostMapping("/group/create/submit")
    public String createGroup(@ModelAttribute("groupEntity") GroupEntity newGroup, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        newGroup.setOrganizer(UserEntity.signedInUser);
        groupService.createNewGroup(newGroup);
        return "redirect:/groups";
    }

    @GetMapping("/group/edit/{id}/members")
    public String editGroupMembers(@PathVariable("id") int groupId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        Optional<GroupEntity> group = groupService.getGroupById(groupId);
        if (group.isPresent()) {
            model.addAttribute("signedInUser", UserEntity.signedInUser);
            model.addAttribute("group", group.get());
            model.addAttribute("membersCollection", groupService.getAllGroupMembers(groupId));
            return "group_members_list";
        }
        return "redirect:/groups";
    }

    @GetMapping("/group/edit/{id}/individual-expenses")
    public String editGroupIndivExpenses(@PathVariable("id") int groupId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        Optional<GroupEntity> group = groupService.getGroupById(groupId);
        if (group.isPresent()) {
            model.addAttribute("signedInUser", UserEntity.signedInUser);
            model.addAttribute("group", group.get());
            model.addAttribute("individualExpenses", individualExpenseService.getAllIndividualExpenses(groupId));
            int expensesSum = individualExpenseService.getTotalSum(groupId) + groupExpenseService.getTotalSum(groupId);
            model.addAttribute("totalSum", expensesSum);
            return "group_indiv_expenses_list";
        }
        return "redirect:/groups";
    }

    @GetMapping("/group/edit/{id}/group-expenses")
    public String editGroupGrExpenses(@PathVariable("id") int groupId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        Optional<GroupEntity> group = groupService.getGroupById(groupId);
        if (group.isPresent()) {
            model.addAttribute("signedInUser", UserEntity.signedInUser);
            model.addAttribute("group", group.get());
            model.addAttribute("groupExpenses", groupExpenseService.getAllGroupExpenses(groupId));
            model.addAttribute("isParticipantMap", groupExpenseService.getIsExpenseParticipantMap(UserEntity.signedInUser.getIdUser(), groupId));
            return "group_gr_expenses_list";
        }
        return "redirect:/groups";
    }

    @GetMapping("/group/delete/{id}")
    public String deleteGroup(@PathVariable("id") int groupId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        groupService.deleteGroup(groupId);
        return "redirect:/groups";
    }
}
