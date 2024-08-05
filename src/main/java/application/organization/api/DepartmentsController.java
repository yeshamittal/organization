package application.organization.api;

import application.organization.persistence.Department;
import application.organization.services.CommonManagementService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Validated
@RequestMapping("/departments")
public class DepartmentsController {
    private final CommonManagementService<Department> departmentService;

    public DepartmentsController(CommonManagementService<Department> commonManagementService) {
        this.departmentService = commonManagementService;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    @PostMapping
    public Department createDepartment(@Valid @RequestBody Department department) {
        return departmentService.create(department);
    }

    @PutMapping
    public Department updateDepartment(@Valid @RequestBody Department department) {
        return departmentService.update(department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.delete(id);
    }
}
