package com.example.controllers;

import com.example.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    final UserService userService;

    public ServiceController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/service/approve")
    public String serviceData(@RequestParam String email) {
        return userService.approveUser(email) ? "Approved successfully" : "Approve unsuccessful";
    }

}

