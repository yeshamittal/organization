package application.organization.services;

import application.organization.entities.Department;
import application.organization.entities.Employee;
import application.organization.exceptions.InvalidActionException;
import application.organization.exceptions.NotFoundException;
import application.organization.repositories.DepartmentRepository;
import application.organization.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department department) {
        if (!departmentRepository.existsById(department.getId())) {
            throw new NotFoundException("Department not found with ID: " + department.getId());
        }

        Department existingDepartment = departmentRepository.findById(department.getId()).get();
        if(existingDepartment.getReadOnly()){
            throw new InvalidActionException("Read-only Department cannot be updated.");
        }

        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new NotFoundException("Department not found with ID: " + id);
        }
        Department department = departmentRepository.findById(id).get();
        if(department.getMandatory()){
            throw new InvalidActionException("Mandatory Department cannot be deleted.");
        }

        for (Employee employee : department.getEmployees()) {
            employee.getDepartments().remove(department);
        }
        departmentRepository.deleteById(id);
    }
}
