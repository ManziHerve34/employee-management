package com.example.employeemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.employeemanagement.modal.AppUser;
import com.example.employeemanagement.modal.Employee;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/auth/login-page";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        // Invalidate the session
        session.invalidate();

        // Clear the cookie by setting it to null and setting the expiration time to a past date
        Cookie usernameCookie = new Cookie("username", null);
        usernameCookie.setMaxAge(0);  // This will delete the cookie
        usernameCookie.setPath("/");  // Make sure the cookie path is the root
        response.addCookie(usernameCookie);  // Add the cookie to the response

        return "redirect:/auth/login-page";  // Redirect to the login page
    }

    @GetMapping("/adashboard")
    public String showADashboard() {
        return "adashboard";
    }

    // Display dashboard page after login
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        AppUser loggedInUser = (AppUser) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("message", "Please log in to access the dashboard.");
            redirectAttributes.addFlashAttribute("successful", false);
            return "redirect:/auth/login-page";
        }

        // Redirect based on role
        if ("admin".equalsIgnoreCase(loggedInUser.getRole())) {
            return "redirect:/adashboard"; // Redirect to admin dashboard
        }

        // Add empty employee object to model for regular users
        model.addAttribute("employee", new Employee());
        return "dashboard"; // Regular user dashboard
    }

}
