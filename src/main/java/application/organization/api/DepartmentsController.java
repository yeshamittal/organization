package application.organization.api;

import application.organization.persistence.entities.Department;
import application.organization.services.CommonManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {
    @Autowired
    private CommonManagementService<Department> departmentService;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        if(Objects.isNull(id)){
            throw new IllegalArgumentException("Id is null");
        }
        return departmentService.getById(id);
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        if(Objects.isNull(department)){
            throw new IllegalArgumentException("Input is null");
        }
        return departmentService.create(department);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        if(Objects.isNull(id) || Objects.isNull(department)){
            throw new IllegalArgumentException("Null values not allowed");
        }
        return departmentService.update(department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Id is null");
        }
        departmentService.delete(id);
    }
}
