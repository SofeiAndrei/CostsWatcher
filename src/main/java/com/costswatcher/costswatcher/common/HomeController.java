package com.costswatcher.costswatcher.common;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import com.costswatcher.costswatcher.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
  private final UserService userService;

  @GetMapping("/")
  public String home(Model model, Authentication authentication) {
    model.addAttribute("title", "Home");
    return "home";
  }
  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("title", "Login");
    return "login";
  }

  @GetMapping("/register")
  public String register(Model model) {
    model.addAttribute("title", "Register");
    return "register";
  }
}
