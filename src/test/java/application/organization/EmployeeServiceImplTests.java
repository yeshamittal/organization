package application.organization;

import application.organization.exceptions.NotFoundException;
import application.organization.persistence.Department;
import application.organization.persistence.DepartmentRepository;
import application.organization.persistence.Employee;
import application.organization.persistence.EmployeeRepository;
import application.organization.services.EmployeeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTests {
    @Mock
    private EmployeeRepository mockEmployeeRepository;

    @Mock
    private DepartmentRepository mockDepartmentRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee emptyEmployee;
    private Employee newEmployeeWithValidDetails;
    private Employee existingEmployee;
    private Employee employeeWithInvalidDepartment;
    private Department invalidDepartment;
    private Department validDepartment;
    private Department mandatoryDepartment;
    private HashSet<Department> invalidDepartmentsSet;
    private List<Department> validDepartmentsList;
    private List<Department> mandatoryDepartmentsList;
    Logger logger = LoggerFactory.getLogger(EmployeeServiceImplTests.class);

    @BeforeEach
    public void setUp() {
        emptyEmployee = Employee.builder().build();
        existingEmployee = Employee.builder().nameFirst("Siddhant").nameLast("Jain").build();
        newEmployeeWithValidDetails = Employee.builder().nameFirst("Yesha").nameLast("Mittal").departments(new HashSet<>()).build();
        employeeWithInvalidDepartment = Employee.builder().nameFirst("Surbhi").nameLast("Jain").build();


        validDepartment = Department.builder().id(2L).name("Marketing").readOnly(true).mandatory(true).build();
        validDepartmentsList = new ArrayList<>();
        validDepartmentsList.add(validDepartment);

        invalidDepartment = Department.builder().name("CSE").id(3L).build();
        invalidDepartmentsSet = new HashSet<>();
        invalidDepartmentsSet.add(invalidDepartment);

        mandatoryDepartment = Department.builder().id(1L).name("Organization").readOnly(true).mandatory(true).build();;
        mandatoryDepartmentsList = new ArrayList<>();
        mandatoryDepartmentsList.add(mandatoryDepartment);

        newEmployeeWithValidDetails.setDepartments(new HashSet<>(validDepartmentsList));
        employeeWithInvalidDepartment.setDepartments(invalidDepartmentsSet);

        logger.info(invalidDepartment.toString());
        logger.info(employeeWithInvalidDepartment.toString());
    }

    @AfterEach
    public void tearDown() {
        emptyEmployee = null;
        newEmployeeWithValidDetails = null;
        employeeWithInvalidDepartment = null;
        validDepartment = null;
        validDepartmentsList = null;
        invalidDepartment = null;
        invalidDepartmentsSet = null;
        mandatoryDepartment = null;
        mandatoryDepartmentsList = null;
    }

    @Test
    @Disabled
    public void testCreate_emptyInput_failure(){
        Employee result = employeeService.create(emptyEmployee);
        logger.info(result.toString());
//        doubts: Validations for nameFirst, nameLast and departments are present at controller level.
//        So, is it required to be tested at service? OR should we test only with valid input? OR should we test for all
//        the validations again at service layer?
    }

    @Test
    public void testCreate_validInput_success(){
        List<Long> departmentIds = validDepartmentsList.stream().map(Department::getId).toList();
        when(mockDepartmentRepository.findAllById(departmentIds)).thenReturn(new ArrayList<>(validDepartmentsList));
        when(mockEmployeeRepository.save(newEmployeeWithValidDetails)).thenReturn(newEmployeeWithValidDetails);

        Employee result = employeeService.create(newEmployeeWithValidDetails);
        logger.info(result.toString());
        assertEquals(newEmployeeWithValidDetails.getNameFirst(), result.getNameFirst());
    }

    @Test
    public void testCreate_invalidDepartment_departmentDoesNotExist_failure(){
        List<Long> invalidDepartmentIds = invalidDepartmentsSet.stream().map(Department::getId).toList();
        when(mockDepartmentRepository.findAllById(invalidDepartmentIds)).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> employeeService.create(employeeWithInvalidDepartment));
    }

    @Test
    public void testCreate_addMandatoryDepartments_success(){
        List<Long> departmentIds = validDepartmentsList.stream().map(Department::getId).toList();
        when(mockDepartmentRepository.findAllById(departmentIds)).thenReturn(new ArrayList<>(validDepartmentsList));
        when(mockDepartmentRepository.findDepartmentsByMandatoryTrue()).thenReturn(mandatoryDepartmentsList);
        when(mockEmployeeRepository.save(newEmployeeWithValidDetails)).thenReturn(newEmployeeWithValidDetails);

        Employee result = employeeService.create(newEmployeeWithValidDetails);
        logger.info(result.toString());
        assertEquals(result.getDepartments().size(), validDepartmentsList.size() + mandatoryDepartmentsList.size());
    }

    @Test
    public void testUpdate_idNotPresent_failure(){
        assertThrows(NotFoundException.class, () -> employeeService.update(existingEmployee));
    }

//    @Test
//    public void testUpdate_employeeDoesNotExist_failure(){
////        when(mockEmployeeRepository.existsById(newEmployeeWithValidDetails.getId())).thenReturn(false);
//        assertThrows(NotFoundException.class, () -> employeeService.update(existingEmployee));
//    }

    @Test
    public void testUpdate_invalidDepartment_departmentDoesNotExist_failure(){

    }

    @Test
    public void testUpdate_addMandatoryDepartments_success(){

    }

    @Test
    public void testUpdate_validInput_success(){

    }

}
