package application.organization;

import application.organization.persistence.entities.Department;
import application.organization.persistence.entities.Employee;
import application.organization.persistence.repositories.EmployeeRepository;
import application.organization.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceTests.class);

    void index(){
        HashSet<Department> departments = new HashSet<>();
        Department department1 = getDepartment("Marketing", 2);
        departments.add(department1);

        Employee employee1 = getEmployee("Yesha", "Mittal", departments);
        Employee employee2 = getEmployee("Siddhant", "Jain", departments);

        List<Employee> mockedResponse = new ArrayList<>();
        mockedResponse.add(employee1);
        mockedResponse.add(employee2);

        when(employeeRepository.findAll()).thenReturn(mockedResponse);

        List<Employee> employees = employeeService.getAll();
        logger.info(employees.toString());

        assertEquals(employees.size(), mockedResponse.size());
    }

    void testGetEmployeeById(){

    }


//    Should create an employee
//    Should create a mapping of employee and readonly dptts
    @Test
    void testCreateEmployee() {
        Employee employee = getEmployee("Yesha", "Mittal", new HashSet<>());
        when(employeeRepository.save(employee)).thenReturn(employee);

        List<Employee> employees = employeeService.getAll();
    }

    void testUpdateEmployee(){

    }

    private Employee getEmployee(String nameFirst, String nameLast, HashSet<Department> departments){
        Employee employee = new Employee();
        employee.setNameFirst(nameFirst);
        employee.setNameLast(nameLast);
        employee.setDepartments(departments);

        return employee;
    }

    private Department getDepartment(String name, Integer id){
        Department department = new Department();
        department.setName(name);
        department.setId(Long.valueOf(id));
        return department;
    }
}
