package com.example.Assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Assignment.entites.User;
import com.example.Assignment.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user_list";
    }

    @GetMapping("/users/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user") User user) {
        if (user.getEnabled() == false) {
            // If user is disabled, don't allow save
            return "redirect:/users?error=disabled";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null || !user.getEnabled()) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "user_form";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("user") User user) {
        if (user.getEnabled() == false) {
            
            return "redirect:/users?error=disabled";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    @PostMapping("/users/enable-disable/{id}")
    @ResponseBody
    public String enableDisableUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setEnabled(!user.getEnabled());
            userService.saveUser(user);
            return "Success";
        }
        return "Failure";
    }
}
