package application.organization.services;

import application.organization.persistence.entities.Department;
import application.organization.persistence.entities.Employee;
import application.organization.exceptions.NotFoundException;
import application.organization.persistence.repositories.DepartmentRepository;
import application.organization.persistence.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService implements CommonManagementService<Employee> {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return employee.get();
        }
        else{
            throw new NotFoundException("Employee not found");
        }
    }

    @Override
    public Employee create(Employee employee) {
        employeeMandatoryDepartments(employee);
        validateDepartments(employee.getDepartments());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (!employeeRepository.existsById(employee.getId())) {
            throw new NotFoundException("Employee not found with ID: " + employee.getId());
        }

        validateDepartments(employee.getDepartments());
        employeeMandatoryDepartments(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new NotFoundException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    private void employeeMandatoryDepartments(Employee employee){
        List<Department> departments = departmentRepository.findDepartmentsByMandatoryTrue();
        employee.getDepartments().addAll(departments);
    }

    private void validateDepartments(Set<Department> departments){
        List<Long> departmentIds = departments.stream().map(Department::getId).toList();
        boolean validDepartments = validDepartmentIds(departmentIds);
        if(!validDepartments){
            throw new NotFoundException("Department not found");
        }
    }

    private boolean validDepartmentIds(List<Long> departmentIds){
        List<Department> departments = departmentRepository.findAllById(departmentIds);
        return departments.size() == departmentIds.size();
    }
}
