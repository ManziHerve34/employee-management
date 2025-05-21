package com.example.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeemanagement.modal.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Add custom query methods here if needed
}
