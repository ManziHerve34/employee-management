package com.example.employeemanagement.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.modal.Employee;
import com.example.employeemanagement.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    static final Logger logger = LogManager.getLogger(EmployeeController.class.getName());

    @Autowired
    private EmployeeService employeeService;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");
        return employeeService.getAllEmployees();
    }

    // Get a single employee by ID
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        logger.info("Fetching employee with id: " + id);
        return employeeService.getEmployee(id);
    }

    // Create a new employee
    @PostMapping
    public void createEmployee(@RequestBody Employee employee) {
        logger.info("Creating new employee: " + employee.getName());
        employeeService.addEmployee(employee);
    }

    // Update an existing employee
    @PutMapping("/update/{id}")
    public void updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        logger.info("Updating employee with id: " + id);
        employeeService.updateEmployee(employee, id);
    }

    // Patch employee (partial update)
    @PatchMapping("/patch/{id}")
    public void patchEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        logger.info("Patching employee with id: " + id);
        employeeService.patchEmployee(employee, id);
    }

    // Delete a single employee
    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        logger.info("Deleting employee with id: " + id);
        employeeService.deleteEmployeeByID(id);
    }

    // Delete all employees
    @DeleteMapping("/detete-all")
    public void deleteAllEmployees() {
        logger.info("Deleting all employees");
        employeeService.deleteAllEmployees();
    }
}
