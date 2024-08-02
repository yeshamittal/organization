package application.organization;

import application.organization.entities.Department;
import application.organization.entities.Employee;
import application.organization.repositories.EmployeeRepository;
import application.organization.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceTests.class);

    @Test
    void testGetAllEmployees() {
        HashSet<Department> departments = new HashSet<>();
        Department department1 = getDepartment("Marketing", 2);
        departments.add(department1);

        Employee employee1 = getEmployee("Yesha", "Mittal", departments);
        Employee employee2 = getEmployee("Siddhant", "Jain", departments);

        List<Employee> mockedResponse = new ArrayList<>();
        mockedResponse.add(employee1);
        mockedResponse.add(employee2);

        when(employeeRepository.findAll()).thenReturn(mockedResponse);

        List<Employee> employees = employeeService.getAllEmployees();
        logger.info(employees.toString());

        assertThat(employees.size()).isEqualTo(mockedResponse.size());
    }

    void testGetEmployeeById(){
        Employee employee = new Employee();
        employee.setNameFirst("Djokovic");
        employee.setNameLast("Serbia");
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