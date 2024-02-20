package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String mainPage() {
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String getMain(Principal principal, Model model) {
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("this_user", userService.findByUsername(principal.getName()));
        model.addAttribute("new_user", new User());
        model.addAttribute("roles", roleService.listRoles());
        return "admin";
    }

    @GetMapping("/user")
    public String getUsers(Principal principal, Model model) {
        model.addAttribute("this_user", userService.findByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("admin/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.listRoles());
        return "admin/new";
    }

    @PostMapping("/admin")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }

        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    @ResponseBody
    public User update(Long id) {

        return userService.findUserById(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(User user, @RequestParam("id") Long id) {
        userService.updateUser(id, user);

        return "redirect:/admin";
    }

    @GetMapping("admin/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        User user = userService.findUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.listRoles());

        System.out.println("user edit " + user);

        return "admin/update";
    }

    @PostMapping("admin/update/")
    public String editUser(@ModelAttribute("new_user") User user) {
//        model.addAttribute("id", userService.findUserById(id).getId());
//        model.addAttribute("username", userService.findUserById(id).getUsername());
//        model.addAttribute("email", userService.findUserById(id).getEmail());
//        model.addAttribute("password", userService.findUserById(id).getPassword());
//        model.addAttribute("roles", userService.findUserById(id).getAuthorities());

        userService.save(user);
        return "redirect:/admin";
    }

//    @PostMapping("admin/update")
//    public String editUser(@ModelAttribute("user") @Valid User user,
//                           BindingResult bindingResult,
//                           @RequestParam("id") Long id) {
//        if (bindingResult.hasErrors()) {
//            System.out.println(user);
//            System.out.println(id);
//            System.out.println(bindingResult);
//            return "admin/update";
//        }
//
//        System.out.println("update user" + user);
//
//        userService.updateUser(id, user);
//        return "redirect:/admin";
//    }

    @PostMapping("/delete/")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}