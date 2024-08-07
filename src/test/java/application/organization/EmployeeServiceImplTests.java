package application.organization;
import application.organization.exceptions.NotFoundException;
import application.organization.persistence.Department;
import application.organization.persistence.DepartmentRepository;
import application.organization.persistence.Employee;
import application.organization.persistence.EmployeeRepository;
import application.organization.services.EmployeeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTests {
    public static final String EMPLOYEE_EXISTING_NAME_FIRST          = "Siddhant";
    public static final String EMPLOYEE_EXISTING_NAME_LAST           = "Jain";
    public static final long   EMPLOYEE_EXISTING_ID                  = 1L;
    public static final String EMPLOYEE_NEW_NAME_FIRST               = "Yesha";
    public static final String EMPLOYEE_NEW_NAME_LAST                = "Mittal";
    public static final long   DEPARTMENT_EXISTING_RO_MANDATORY_ID   = 1L;
    public static final String DEPARTMENT_EXISTING_RO_MANDATORY_NAME = "Organization";
    public static final long   DEPARTMENT_EXISITNG_ID                = 2L;
    public static final String DEPARTMENT_NON_EXISTING_NAME          = "CSE";
    public static final long   DEPARTMENT_NON_EXISTING_ID            = 3L;
    public static final String DEPARTMENT_EXISTING_NAME              = "Marketing";
    public static final String EMPLOYEE_NEW_NAME = "New name";

    @Mock
    private EmployeeRepository mockEmployeeRepository;
    @Mock
    private DepartmentRepository mockDepartmentRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee         newEmployee;
    private Employee         existingEmployee;
    private Department       nonExistingDepartment;
    private Department       existingDepartment_readOnly_mandatory;
    private Department       existingDepartment;

    @BeforeEach
    public void setUp() {
        existingEmployee = Employee.builder().id(EMPLOYEE_EXISTING_ID).nameFirst(EMPLOYEE_EXISTING_NAME_FIRST).nameLast(EMPLOYEE_EXISTING_NAME_LAST).departments(new HashSet<>()).build();
        newEmployee      = Employee.builder().id(null).nameFirst(EMPLOYEE_NEW_NAME_FIRST).nameLast(EMPLOYEE_NEW_NAME_LAST).departments(new HashSet<>()).build();
        existingDepartment_readOnly_mandatory = Department.builder().id(DEPARTMENT_EXISTING_RO_MANDATORY_ID).name(DEPARTMENT_EXISTING_RO_MANDATORY_NAME).readOnly(true).mandatory(true).build();
        existingDepartment                    = Department.builder().id(DEPARTMENT_EXISITNG_ID).name(DEPARTMENT_EXISTING_NAME).readOnly(false).mandatory(false).build();
        nonExistingDepartment = Department.builder().id(DEPARTMENT_NON_EXISTING_ID).name(DEPARTMENT_NON_EXISTING_NAME).build();

        lenient().when(mockDepartmentRepository.findAllById(List.of(DEPARTMENT_EXISITNG_ID, DEPARTMENT_EXISTING_RO_MANDATORY_ID)))
                .thenReturn(List.of(existingDepartment, existingDepartment_readOnly_mandatory));
        lenient().when(mockDepartmentRepository.findAllById(List.of(DEPARTMENT_EXISITNG_ID)))
                .thenReturn(List.of(existingDepartment));
        lenient().when(mockDepartmentRepository.findDepartmentsByMandatoryTrue()).thenReturn(List.of(existingDepartment_readOnly_mandatory));

        lenient().when(mockEmployeeRepository.save(newEmployee)).thenReturn(newEmployee);
        lenient().when(mockEmployeeRepository.save(existingEmployee)).thenReturn(existingEmployee);
        lenient().when(mockEmployeeRepository.existsById(existingEmployee.getId())).thenReturn(true);
    }

    private void withExistingDepartment(final Employee employee) {
        employee.getDepartments().add(existingDepartment);
    }

    private void withNonExistingDepartment(final Employee employee) {
        employee.getDepartments().add(nonExistingDepartment);
    }

    @AfterEach
    public void tearDown() {
        existingEmployee                      = null;
        newEmployee                           = null;
        existingDepartment_readOnly_mandatory = null;
        nonExistingDepartment                 = null;
        existingDepartment                    = null;
    }

    @Test
    public void testCreate_validInput_success() {
        withExistingDepartment(newEmployee);
        Employee result = employeeService.create(newEmployee);
        assertEquals(newEmployee.getNameFirst(), result.getNameFirst());
        assertEquals(newEmployee.getId(), result.getId());
    }

    @Test
    public void testCreate_invalidDepartment_departmentDoesNotExist_failure() {
        withNonExistingDepartment(newEmployee);
        assertThrows(NotFoundException.class, () -> employeeService.create(newEmployee));
    }

    @Test
    public void testCreate_addMandatoryDepartments_success() {
        withExistingDepartment(newEmployee);
        Employee result = employeeService.create(newEmployee);

        for(Department department : result.getDepartments()) {
            if(department.getName().equals(DEPARTMENT_EXISTING_RO_MANDATORY_NAME)) {
                assertTrue(true);
                return;
            }
        }
        fail();
    }

    @Test
    public void testUpdate_idNotPresent_failure() {
        assertThrows(NotFoundException.class, () -> employeeService.update(newEmployee));
    }

    @Test
    public void testUpdate_invalidDepartment_departmentDoesNotExist_failure() {
        withNonExistingDepartment(existingEmployee);
        assertThrows(NotFoundException.class, () -> employeeService.update(existingEmployee));
    }

    @Test
    public void testUpdate_addMandatoryDepartments_success() {
        withExistingDepartment(existingEmployee);
        Employee result = employeeService.update(existingEmployee);

        for(Department department : result.getDepartments()) {
            if(department.getName().equals(DEPARTMENT_EXISTING_RO_MANDATORY_NAME)) {
                assertTrue(true);
                return;
            }
        }
        fail();
    }

    @Test
    public void testUpdate_validInput_success() {
        withExistingDepartment(existingEmployee);
        existingEmployee.setNameFirst(EMPLOYEE_NEW_NAME);
        Employee result = employeeService.update(existingEmployee);
        assertEquals(existingEmployee.getNameFirst(), result.getNameFirst());
        assertEquals(existingEmployee.getId(), result.getId());
    }
    @Test
    public void testDelete_employeeDoesNotExist_failure() {
        when(mockEmployeeRepository.existsById(newEmployee.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> employeeService.delete(newEmployee.getId()));
    }
}