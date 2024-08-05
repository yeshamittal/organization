package application.organization.api;

import application.organization.persistence.entities.Department;
import application.organization.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentsController {
    private final DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        if(Objects.isNull(id)){
            throw new IllegalArgumentException("Id is null");
        }
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        if(Objects.isNull(department)){
            throw new IllegalArgumentException("Input is null");
        }
        return departmentService.createDepartment(department);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        if(Objects.isNull(id) || Objects.isNull(department)){
            throw new IllegalArgumentException("Null values not allowed");
        }
        return departmentService.updateDepartment(department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Id is null");
        }
        departmentService.deleteDepartment(id);
    }
}
