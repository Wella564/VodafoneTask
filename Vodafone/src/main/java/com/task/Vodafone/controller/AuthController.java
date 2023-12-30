package com.task.Vodafone.controller;

import jakarta.validation.Valid;
import com.task.Vodafone.dto.UserDto;
import com.task.Vodafone.entity.User;
import com.task.Vodafone.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller//Marks a class as a Spring MVC controller,
// handling HTTP requests and defining methods to process them.
//using Rest APIs (POST,GET,PUT,DELETE)

public class AuthController {

    private UserService userService;
    // for Registration methods (saving users and searching for a user or all users)
    public AuthController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/loggedin")
    public String loggedin(){
        return "loggedin";
    }

    // handler method to handle user registration request
    @GetMapping("/register")//model to view aspects from the backend on the frontend without hardcoding the frontend
    public String showRegistrationForm(Model model)
    {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,//valid: checks constraints placed in userdto(@email,@notempty,..)
                               //@modelattribute("user"):add userDto to the model under the name user. by adding it as a model it can viewed in .html
                               BindingResult result,// to record error messages if any were there works with @valid
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) //boolean if there was an error"duplicate email"
        {
            model.addAttribute("user", user);
            return "register";// if there are errors return the user to reg page
        }
        userService.saveUser(user);
        return "redirect:/register?success";//else if no errors save
    }

    @GetMapping("/loggedin/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }




}
