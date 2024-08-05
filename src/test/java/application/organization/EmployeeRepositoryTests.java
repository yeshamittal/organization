package application.organization;

import application.organization.persistence.entities.Department;
import application.organization.persistence.entities.Employee;
import application.organization.persistence.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeRepositoryTests.class);

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        logger.info(employees.toString());
    }

    @Test
    void testGetEmployeeById() {
        Employee employeeFound = employeeRepository.findById(Long.valueOf(1)).orElse(null);
        assertThat(employeeFound).isNotNull();
        if(!Objects.isNull(employeeFound)){
            assertThat(employeeFound.getNameFirst()).isEqualTo("Djokovic");
        }
    }

    @Test
    void testCreateEmployee() {
        Employee employee = getEmployee("Yesha", "Mittal", null);
        employeeRepository.save(employee);

        Employee employeeFound = employeeRepository.findById(employee.getId()).orElse(null);

        assertThat(employeeFound).isNotNull();
        if(!Objects.isNull(employeeFound)){
            assertThat(employeeFound.getNameFirst()).isEqualTo("Yesha");
        }
    }

    @Test
    void testUpdateEmployee(){
        int id = 1;
        Employee employeeFound = employeeRepository.findById(Long.valueOf(id)).orElse(null);
        assertThat(employeeFound.getNameFirst()).isEqualTo("Djokovic");

        employeeFound.setNameFirst("Yesha");
        employeeRepository.save(employeeFound);
        employeeFound = employeeRepository.findById(Long.valueOf(id)).orElse(null);

        assertThat(employeeFound.getNameFirst()).isEqualTo("Yesha");
    }

    @Test
    void testDeleteEmployee(){
        int id = 1;
        Employee employeeFound = employeeRepository.findById(Long.valueOf(id)).orElse(null);
        assertThat(employeeFound).isNotNull();

        employeeRepository.delete(employeeFound);

        employeeFound = employeeRepository.findById(Long.valueOf(id)).orElse(null);
        assertThat(employeeFound).isNull();
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