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
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
}
