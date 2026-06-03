package com.security.learning.controller;

import com.security.learning.entity.User;
import com.security.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(
            @RequestBody User user) {

        return userService.register(user);
    }

    @GetMapping("/profile")
    public String profile() {

        return "Protected Profile API";
    }
}
