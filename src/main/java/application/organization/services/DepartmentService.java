package application.organization.services;

import application.organization.persistence.Department;
import application.organization.persistence.Employee;
import application.organization.exceptions.InvalidActionException;
import application.organization.exceptions.NotFoundException;
import application.organization.persistence.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService implements CommonManagementService<Department> {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Department not found"));
    }

    @Override
    public Department create(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department update(Department department) {
        if (!departmentRepository.existsById(department.getId())) {
            throw new NotFoundException("Department not found with ID: " + department.getId());
        }

        Department existingDepartment = departmentRepository.findById(department.getId()).get();
        if(existingDepartment.getReadOnly()){
            if(department.getReadOnly()){
                throw new InvalidActionException("Set read-only flag to false to make any update");
            }
        }
        return departmentRepository.save(department);
    }

    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new NotFoundException("Department not found with ID: " + id);
        }
        Department department = departmentRepository.findById(id).get();
        if(department.getReadOnly()){
            throw new InvalidActionException("Update department with read-only as false to delete");
        }

        for (Employee employee : department.getEmployees()) {
            employee.getDepartments().remove(department);
        }
        departmentRepository.deleteById(id);
    }
}
