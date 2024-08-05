package application.organization.services;

import application.organization.persistence.entities.Department;
import application.organization.persistence.entities.Employee;
import application.organization.exceptions.InvalidActionException;
import application.organization.exceptions.NotFoundException;
import application.organization.persistence.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            return department.get();
        }
        else{
            throw new NotFoundException("Department not found");
        }
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Department department) {
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

    public void deleteDepartment(Long id) {
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
