package com.example.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.employeemanagement.modal.AppUser;
import com.example.employeemanagement.service.AppUserService;

@RestController
@RequestMapping("/app_users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public List<AppUser> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/{username}")
    public AppUser getUserByUsername(@PathVariable String username) {
        return appUserService.getByUsername(username);
    }
}
