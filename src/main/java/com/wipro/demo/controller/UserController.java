package com.wipro.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wipro.demo.entity.User;
import com.wipro.demo.service.UserService;


@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return userService.signup(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }
}

