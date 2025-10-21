// EmployeeManagementApp.java - Main application with menu
package com.employee.app;

import com.employee.service.EmployeeService;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagementApp {
    private EmployeeService employeeService;
    private Scanner scanner;

    public EmployeeManagementApp() {
        this.employeeService = new EmployeeService();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    viewByDepartment();
                    break;
                case 7:
                    showAverageSalary();
                    break;
                case 8:
                    running = false;
                    System.out.println("Thank you for using Employee Management System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            System.out.println();
        }
        
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("=== Employee Management System ===");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Search Employee by ID");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. View Employees by Department");
        System.out.println("7. Show Average Salary");
        System.out.println("8. Exit");
        System.out.println("==================================");
    }

    private void addEmployee() {
        System.out.println("\n--- Add New Employee ---");
        int id = getIntInput("Enter Employee ID: ");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        double salary = getDoubleInput("Enter Salary: ");
        
        Employee emp = new Employee(id, name, department, salary);
        employeeService.addEmployee(emp);
    }

    private void viewAllEmployees() {
        System.out.println("\n--- All Employees ---");
        List<Employee> employees = employeeService.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }

    private void searchEmployee() {
        System.out.println("\n--- Search Employee ---");
        int id = getIntInput("Enter Employee ID: ");
        
        employeeService.findEmployeeById(id).ifPresentOrElse(
            emp -> System.out.println("Found: " + emp),
            () -> System.out.println("Employee not found with ID: " + id)
        );
    }

    private void updateEmployee() {
        System.out.println("\n--- Update Employee ---");
        int id = getIntInput("Enter Employee ID to update: ");
        
        if (employeeService.findEmployeeById(id).isPresent()) {
            System.out.print("Enter New Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter New Department: ");
            String department = scanner.nextLine();
            double salary = getDoubleInput("Enter New Salary: ");
            
            employeeService.updateEmployee(id, name, department, salary);
        } else {
            System.out.println("Employee not found with ID: " + id);
        }
    }

    private void deleteEmployee() {
        System.out.println("\n--- Delete Employee ---");
        int id = getIntInput("Enter Employee ID to delete: ");
        employeeService.deleteEmployee(id);
    }

    private void viewByDepartment() {
        System.out.println("\n--- View by Department ---");
        System.out.print("Enter Department Name: ");
        String department = scanner.nextLine();
        
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        
        if (employees.isEmpty()) {
            System.out.println("No employees found in department: " + department);
        } else {
            System.out.println("Employees in " + department + ":");
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }

    private void showAverageSalary() {
        double avgSalary = employeeService.calculateAverageSalary();
        System.out.println("\n--- Average Salary ---");
        System.out.printf("Average Salary: %.2f%n", avgSalary);
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return value;
    }

    public static void main(String[] args) {
        EmployeeManagementApp app = new EmployeeManagementApp();
        app.run();
    }

    public static class Employee {
        private int id;
        private String name;
        private String department;
        private double salary;

        // Constructor
        public Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public double getSalary() {
            return salary;
        }

        // Setters
        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return String.format("Employee[ID=%d, Name=%s, Dept=%s, Salary=%.2f]",
                               id, name, department, salary);
        }
    }
}