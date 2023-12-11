package com.costswatcher.costswatcher.group;

import com.costswatcher.costswatcher.user.UserEntity;
import com.costswatcher.costswatcher.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GroupMemberController {
    private final GroupMemberService groupMemberService;
    private final UserService userService;

    @Autowired
    public GroupMemberController(GroupMemberService groupMemberService, UserService userService) {
        this.groupMemberService = groupMemberService;
        this.userService = userService;
    }

    @GetMapping("/group/{id}/add/member")
    public String addGroupMemberPage(@PathVariable("id") int groupId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        model.addAttribute("groupId", groupId);
        model.addAttribute("userEntity", new UserEntity());
        return "add_group_member";
    }

    @PostMapping("/group/{id}/add/member/submit")
    public String addGroupMember(@PathVariable("id") int groupId, @ModelAttribute("userEntity") UserEntity newMember, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        boolean exists = userService.checkIfUserExists(newMember.getUsername());
        if (!exists) {
            model.addAttribute("invalidUsername", "Username doesn't exist!");
            model.addAttribute("groupId", groupId);
            model.addAttribute("userEntity", new UserEntity());
            return "add_group_member";
        }
        boolean memberExists = groupMemberService.checkIfGroupHasMember(groupId, newMember.getUsername());
        if (memberExists) {
            model.addAttribute("invalidUsername", "The user is already a member of this group!");
            model.addAttribute("groupId", groupId);
            model.addAttribute("userEntity", new UserEntity());
            return "add_group_member";
        }
        int userId = userService.getUserByUsername(newMember.getUsername()).get().getIdUser();
        groupMemberService.addNewGroupMember(new GroupMember(new GroupMemberId(userId, groupId), false));
        return "redirect:/groups";
    }

    @GetMapping("/group/{id}/leave")
    public String leaveGroup(@PathVariable("id") int groupId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        groupMemberService.removeGroupMember(new GroupMemberId(UserEntity.signedInUser.getIdUser(), groupId));
        return "redirect:/groups";
    }
}