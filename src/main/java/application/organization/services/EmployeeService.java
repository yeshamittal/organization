package application.organization.services;

import application.organization.persistence.Department;
import application.organization.persistence.Employee;
import application.organization.exceptions.NotFoundException;
import application.organization.persistence.DepartmentRepository;
import application.organization.persistence.EmployeeRepository;
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
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    @Override
    public Employee create(Employee employee) {
        checkIfDepartmentsExist(employee.getDepartments());
        addMandatoryDepartmentsToEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (!employeeRepository.existsById(employee.getId())) {
            throw new NotFoundException("Employee not found with ID: " + employee.getId());
        }

        checkIfDepartmentsExist(employee.getDepartments());
        addMandatoryDepartmentsToEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new NotFoundException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    private void checkIfDepartmentsExist(Set<Department> departments){
        List<Long> departmentIds = departments.stream().map(Department::getId).toList();
        boolean departmentsExist = checkIfDepartmentIdsExist(departmentIds);
        if(!departmentsExist){
            throw new NotFoundException("Department not found");
        }
    }

    private void addMandatoryDepartmentsToEmployee(Employee employee){
        List<Department> departments = departmentRepository.findDepartmentsByMandatoryTrue();
        employee.getDepartments().addAll(departments);
    }

    private boolean checkIfDepartmentIdsExist(List<Long> departmentIds){
        List<Department> departments = departmentRepository.findAllById(departmentIds);
        return departments.size() == departmentIds.size();
    }
}
