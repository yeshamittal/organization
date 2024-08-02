package application.organization.controllers;

import application.organization.entities.Employee;
import application.organization.exceptions.CommonException;
import application.organization.exceptions.NotFoundException;
import application.organization.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Employee> getAllEmployees() {
        try{
            return employeeService.getAllEmployees();
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        try{
            if(Objects.isNull(id)){
                throw new IllegalArgumentException("id is null");
            }
            Optional<Employee> employee = employeeService.getEmployeeById(id);
            if(employee.isPresent()){
                return employee.get();
            }
            else{
                throw new NotFoundException("Employee not found");
            }
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        try {
            if(Objects.isNull(employee)){
                throw new IllegalArgumentException("Input is null");
            }
            return employeeService.createEmployee(employee);
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try{
            if(Objects.isNull(id) || Objects.isNull(employee)){
                throw new IllegalArgumentException("Null values not allowed");
            }
            return employeeService.updateEmployee(id, employee);
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new IllegalArgumentException("Id is null");
            }
            employeeService.deleteEmployee(id);
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }
}
