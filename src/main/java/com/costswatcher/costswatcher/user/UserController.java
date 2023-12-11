package com.costswatcher.costswatcher.user;

import com.costswatcher.costswatcher.group.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {
    UserService userService;
    GroupService groupService;
    GroupMemberService groupMemberService;

    @Autowired
    public UserController(UserService userService, GroupService groupService, GroupMemberService groupMemberService) {
        this.groupService = groupService;
        this.userService = userService;
        this.groupMemberService = groupMemberService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        return "home";
    }

    @GetMapping("/signin")
    public String signInPage(Model model) {
        if (UserEntity.signedInUser != null)
            return "redirect:/groups";
        model.addAttribute("userEntity", new UserEntity());
        return "signin";
    }

    @GetMapping("/signup")
    public String signUpPage(Model model) {
        if (UserEntity.signedInUser != null)
            return "redirect:/groups";
        model.addAttribute("userEntity", new UserEntity());
        return "signup";
    }

    @PostMapping("/signin/submit")
    public String signInSubmit(Model model, @ModelAttribute("userEntity") UserEntity formUser) {
        if (UserEntity.signedInUser != null)
            return "redirect:/groups";
        boolean succeeded = userService.signInUser(formUser);
        if (succeeded)
            return "redirect:/groups";
        else {
            model.addAttribute("invalidSignIn", "Invalid username or password!");
            return "signin";
        }
    }

    @PostMapping("/signup/submit")
    public String signUpSubmit(@ModelAttribute("userEntity") UserEntity formUser, Model model) {
        if (UserEntity.signedInUser != null)
            return "redirect:/groups";
        boolean succeeded = userService.signUpUser(formUser);
        if (succeeded)
            return "home";
        else {
            model.addAttribute("invalidSignUp", "Username already exists!");
            return "signup";
        }
    }

    @GetMapping("/signout")
    public String signOut(Model model) {
        userService.signOutUser();
        return "redirect:/";
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
        model.addAttribute("groupEntity", new Groups());
        return "create_group_form";
    }

    @PostMapping("/group/create/submit")
    public String createGroup(@ModelAttribute("groupEntity") Groups newGroup, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        groupService.createNewGroup(newGroup);
        return "redirect:/groups";
    }

    @GetMapping("/group/edit/{id}")
    public String editGroup(@PathVariable("id") int groupId, Model model) {
        if (UserEntity.signedInUser == null)
            return "redirect:/";
        Optional<Groups> group = groupService.getGroupById(groupId);
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
