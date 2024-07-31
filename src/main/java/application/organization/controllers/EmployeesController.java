package application.organization.controllers;

import application.organization.entities.Employee;
import application.organization.services.EmployeeService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try{
            List<Employee> allEmployees = employeeService.getAllEmployees();
            return ResponseEntity.ok(allEmployees);
        }catch (Exception e){
            // return ResponseEntity.badRequest().body(new ArrayList<>());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try{
            if(Objects.isNull(id)){
                return ResponseEntity.badRequest().build();
            }
            Optional<Employee> employee = employeeService.getEmployeeById(id);
            return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        System.out.println("=========================");
        System.out.println(employee.toString());
        System.out.println("=========================");
        try {
            if(StringUtils.isEmpty(employee.getFirst_name())){
                return ResponseEntity.badRequest().body("First name cannot be empty");
            }
            if(StringUtils.isEmpty(employee.getLast_name())){
                return ResponseEntity.badRequest().body("Last name cannot be empty");
            }
            employeeService.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try{
            if(Objects.isNull(id) || Objects.isNull(employee)){
                return ResponseEntity.badRequest().build();
            }
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                return ResponseEntity.badRequest().build();
            }
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }
}
