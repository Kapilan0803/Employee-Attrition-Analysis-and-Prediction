// EmployeeService.java - Service class for business logic
package com.employee.service;

import com.employee.app.EmployeeManagementApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private List<EmployeeManagementApp.Employee> employees;

    public EmployeeService() {
        this.employees = new ArrayList<>();
    }

    // Add a new employee
    public void addEmployee(EmployeeManagementApp.Employee employee) {
        employees.add(employee);
        System.out.println("Employee added successfully: " + employee.getName());
    }

    // Get all employees
    public List<EmployeeManagementApp.Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    // Find employee by ID
    public Optional<EmployeeManagementApp.Employee> findEmployeeById(int id) {
        return employees.stream()
                       .filter(e -> e.getId() == id)
                       .findFirst();
    }

    // Update employee
    public boolean updateEmployee(int id, String name, String department, double salary) {
        Optional<EmployeeManagementApp.Employee> empOpt = findEmployeeById(id);
        if (empOpt.isPresent()) {
            EmployeeManagementApp.Employee emp = empOpt.get();
            emp.setName(name);
            emp.setDepartment(department);
            emp.setSalary(salary);
            System.out.println("Employee updated successfully");
            return true;
        }
        System.out.println("Employee not found with ID: " + id);
        return false;
    }

    // Delete employee
    public boolean deleteEmployee(int id) {
        boolean removed = employees.removeIf(e -> e.getId() == id);
        if (removed) {
            System.out.println("Employee deleted successfully");
        } else {
            System.out.println("Employee not found with ID: " + id);
        }
        return removed;
    }

    // Get employees by department
    public List<EmployeeManagementApp.Employee> getEmployeesByDepartment(String department) {
        List<EmployeeManagementApp.Employee> result = new ArrayList<>();
        for (EmployeeManagementApp.Employee emp : employees) {
            if (emp.getDepartment().equalsIgnoreCase(department)) {
                result.add(emp);
            }
        }
        return result;
    }

    // Calculate average salary
    public double calculateAverageSalary() {
        if (employees.isEmpty()) {
            return 0.0;
        }
        double sum = employees.stream()
                             .mapToDouble(EmployeeManagementApp.Employee::getSalary)
                             .sum();
        return sum / employees.size();
    }
}