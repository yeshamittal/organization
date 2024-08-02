package application.organization.controllers;

import application.organization.entities.Department;
import application.organization.exceptions.CommonException;
import application.organization.exceptions.InvalidActionException;
import application.organization.exceptions.NotFoundException;
import application.organization.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments() {
        try{
            return departmentService.getAllDepartments();
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        try{
            if(Objects.isNull(id)){
                throw new IllegalArgumentException("id is null");
            }
            Optional<Department> department = departmentService.getDepartmentById(id);
            if(department.isPresent()){
                return department.get();
            }
            else{
                throw new NotFoundException("Department not found");
            }
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        try {
            if(Objects.isNull(department)){
                throw new IllegalArgumentException("Input is null");
            }
            return departmentService.createDepartment(department);
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        try{
            if(Objects.isNull(id) || Objects.isNull(department)){
                throw new IllegalArgumentException("Null values not allowed");
            }
            return departmentService.updateDepartment(id, department);
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (InvalidActionException e){
            throw new InvalidActionException(e.getMessage());
        } catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new IllegalArgumentException("Id is null");
            }
            departmentService.deleteDepartment(id);
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (InvalidActionException e){
            throw new InvalidActionException(e.getMessage());
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }
}
