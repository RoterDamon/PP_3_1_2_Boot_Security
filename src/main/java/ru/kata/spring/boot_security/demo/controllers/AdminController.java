package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.ERole;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listCustomers(@AuthenticationPrincipal User user, Model theModel) {
        List<User> users = userService.allUsers();
        theModel.addAttribute("user", user);
        theModel.addAttribute("users", users);
        theModel.addAttribute("roles", roleService.getAllRoles());
        return "users";
    }

    @GetMapping("/new")
    public String showFormForAdd(@AuthenticationPrincipal User user, Model theModel) {
        theModel.addAttribute("user", user);
        theModel.addAttribute("roles", roleService.getAllRoles());
        return "new_user";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam(value = "userRolesSelector") String[] selectResult) {
        for (String s : selectResult) {
            user.addRole(roleService.getRoleByName(ERole.valueOf(s)));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String editCustomerForm(@ModelAttribute("user") User user,
                                   @RequestParam(value = "userRolesSelector") String[] selectResult) {
        for (String s : selectResult) {
            if (!user.getRoles().contains(roleService.getRoleByName(ERole.valueOf(s))))
                user.addRole(roleService.getRoleByName(ERole.valueOf(s)));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{userId}")
    public String deleteCustomer(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }
}
