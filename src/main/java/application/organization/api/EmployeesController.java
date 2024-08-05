package application.organization.api;

import application.organization.persistence.entities.Employee;
import application.organization.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeesController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        if(Objects.isNull(id)){
            throw new IllegalArgumentException("Id is null");
        }
        return employeeService.getById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        if(Objects.isNull(employee)){
            throw new IllegalArgumentException("Input is null");
        }
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        if(Objects.isNull(id) || Objects.isNull(employee)){
            throw new IllegalArgumentException("Null values not allowed");
        }
        return employeeService.update(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Id is null");
        }
        employeeService.delete(id);
    }
}
