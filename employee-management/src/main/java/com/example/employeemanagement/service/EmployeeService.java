package com.example.employeemanagement.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employeemanagement.modal.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee, long id) {
        Employee existing = employeeRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(employee.getName());
            existing.setEmail(employee.getEmail());
            existing.setPosition(employee.getPosition());
            existing.setQualification(employee.getQualification());
            employeeRepository.save(existing);
        }
    }

    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    public void deleteEmployeeByID(long id) {
        employeeRepository.deleteById(id);
    }

    public void patchEmployee(Employee employee, long id) {
        Employee existing = employeeRepository.findById(id).orElse(null);
        if (existing != null) {
            if (employee.getName() != null) existing.setName(employee.getName());
            if (employee.getEmail() != null) existing.setEmail(employee.getEmail());
            if (employee.getPosition() != null) existing.setPosition(employee.getPosition());
            if (employee.getQualification() != null) existing.setQualification(employee.getQualification());
            employeeRepository.save(existing);
        }
    }
}

