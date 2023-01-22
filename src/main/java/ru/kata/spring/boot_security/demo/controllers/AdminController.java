package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listCustomers(Model theModel) {
        List<User> users = userService.allUsers();
        theModel.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model theModel) {
        User user = new User();
        theModel.addAttribute("user", user);
        return "new_user";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit/{userId}")
    public ModelAndView editCustomerForm(@PathVariable("userId") Long userId) {
        ModelAndView mav = new ModelAndView("edit_user");
        User user = userService.findUserById(userId);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/delete/{userId}")
    public String deleteCustomer(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }
}
