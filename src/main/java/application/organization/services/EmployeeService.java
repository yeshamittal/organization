package application.organization.services;

import application.organization.entities.Department;
import application.organization.entities.Employee;
import application.organization.repositories.EmployeeDepartmentRepository;
import application.organization.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeDepartmentRepository employeeDepartmentRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
//        validateDepartments();
        employeeDepartmentRepository.save(employee);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found with ID: " + id);
        }
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
