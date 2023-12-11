package com.costswatcher.costswatcher.group;

import com.costswatcher.costswatcher.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public String getGroupsForMember(Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupsCollection", groupService.findAllForUser(UserEntity.signedInUser.getIdUser()));
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
        groupService.createNewGroup(newGroup);
        return "redirect:/groups";
    }

    @GetMapping("/group/edit/{id}")
    public String editGroup(@PathVariable("id") int groupId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        Optional<GroupEntity> group = groupService.getGroupById(groupId);
        if (group.isPresent()) {
            model.addAttribute("group", group.get());
            model.addAttribute("membersCollection", groupService.getAllGroupMembers(groupId));
            return "edit_group";
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
