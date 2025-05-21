package com.example.employeemanagement.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employeemanagement.modal.AppUser;
import com.example.employeemanagement.repository.AppUserRepository;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public void registerUser(AppUser user) {
        // Hash password using jbcrypt before saving
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);
        appUserRepository.save(user);
    }

    public boolean authenticate(String username, String rawPassword) {
        return appUserRepository.findByUsername(username)
                .map(user -> BCrypt.checkpw(rawPassword, user.getPassword()))
                .orElse(false);
    }

    public AppUser getByUsername(String username) {
        return appUserRepository.findByUsername(username).orElse(null);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
}
