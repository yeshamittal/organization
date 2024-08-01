package application.organization.services;

import application.organization.entities.Employee;
import application.organization.exceptions.NotFoundException;
import application.organization.repositories.MapEmployeeDepartmentRepository;
import application.organization.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MapEmployeeDepartmentRepository mapEmployeeDepartmentRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        mapEmployeeDepartmentRepository.save(employee);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        if (!employeeRepository.existsById(employee.getId())) {
            throw new NotFoundException("Employee not found with ID: " + employee.getId());
        }
        employee.setId(employee.getId());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new NotFoundException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
