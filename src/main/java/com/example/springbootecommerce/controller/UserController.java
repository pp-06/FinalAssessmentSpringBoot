package com.example.springbootecommerce.controller;

import com.example.springbootecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // creating a user
    @PostMapping(value = "/registration")
    public String Signup(@RequestBody Map<String,Object> map) {
        return userService.signup(map.get("username").toString(), map.get("password1").toString(), map.get("password2").toString(), map.get("email").toString());
    }

    //login user
    @PostMapping(value = "/login")
    public String login(@RequestBody Map<String,Object> map) {
        return userService.login(map.get("username").toString(), map.get("password").toString());
    }

}
