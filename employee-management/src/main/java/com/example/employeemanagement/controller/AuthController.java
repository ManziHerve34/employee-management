package com.example.employeemanagement.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.employeemanagement.modal.AppUser;
import com.example.employeemanagement.service.AppUserService;
import com.example.employeemanagement.service.UserImageService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AppUserService userService;

    @Autowired
    private UserImageService userImageService;

    // Display login page
    @GetMapping("/login-page")
    public String showLoginPage() {
        return "login";
    }

    // Display registration page
    @GetMapping("/register-page")
    public String showRegisterPage() {
        return "register";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String register(@ModelAttribute AppUser user,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes) {

        if (userService.getByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("message", "Username already taken!");
            redirectAttributes.addFlashAttribute("successful", false);
            return "redirect:/auth/register-page";
        }

        // 1. Register user in PostgreSQL
        userService.registerUser(user);

        try {
            userImageService.saveUserImage(user.getUsername(), image);
        } catch (IOException e) {
            System.out.println(e);
        }

        redirectAttributes.addFlashAttribute("message", "User registered successfully!");
        redirectAttributes.addFlashAttribute("successful", true);
        return "redirect:/auth/login-page";
    }

    // Handle login form submission
    @PostMapping("/login")
    public String login(@ModelAttribute AppUser user,
            RedirectAttributes redirectAttributes,
            HttpSession session,
            HttpServletResponse response) {
        boolean authenticated = userService.authenticate(user.getUsername(), user.getPassword());

        if (authenticated) {
            AppUser loggedInUser = userService.getByUsername(user.getUsername());
            session.setAttribute("loggedInUser", loggedInUser); // Store the logged-in user in session

            // Create a cookie to store the username
            Cookie usernameCookie = new Cookie("username", loggedInUser.getUsername());
            usernameCookie.setMaxAge(60 * 60 * 24);  // Set the cookie to expire in 1 day
            usernameCookie.setHttpOnly(false);  // Allow access to the cookie from JavaScript (not HttpOnly)
            usernameCookie.setSecure(false);  // Set to true if your app is using HTTPS
            usernameCookie.setPath("/");  // Make the cookie accessible across the entire app
            response.addCookie(usernameCookie);  // Add the cookie to the response

            // Redirect based on role
            if ("admin".equalsIgnoreCase(loggedInUser.getRole())) {
                return "redirect:/adashboard";
            } else {
                return "redirect:/dashboard";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Invalid username or password");
            redirectAttributes.addFlashAttribute("successful", false);
            return "redirect:/auth/login-page";
        }
    }

    // Example dashboard page after login
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
