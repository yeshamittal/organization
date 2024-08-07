package application.organization;

import application.organization.exceptions.InvalidActionException;
import application.organization.exceptions.NotFoundException;
import application.organization.persistence.Department;
import application.organization.persistence.DepartmentRepository;
import application.organization.services.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTests {
    public static final long   DEPARTMENT_EXISTING_RO_MANDATORY_ID   = 1L;
    public static final String DEPARTMENT_EXISTING_RO_MANDATORY_NAME = "Organization";
    public static final long   DEPARTMENT_EXISITNG_ID                = 2L;
    public static final String DEPARTMENT_NON_EXISTING_NAME          = "CSE";
    public static final String DEPARTMENT_EXISTING_NAME              = "Marketing";
    public static final String DEPARTMENT_NEW_NAME                   = "New Department";

    @Mock
    private DepartmentRepository mockDepartmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department nonExistingDepartment;
    private Department       existingDepartment_readOnly_mandatory;
    private Department       existingDepartment;

    @BeforeEach
    public void setUp() {
        existingDepartment_readOnly_mandatory = Department.builder().id(DEPARTMENT_EXISTING_RO_MANDATORY_ID).name(DEPARTMENT_EXISTING_RO_MANDATORY_NAME).readOnly(true).mandatory(true).build();
        existingDepartment                    = Department.builder().id(DEPARTMENT_EXISITNG_ID).name(DEPARTMENT_EXISTING_NAME).readOnly(false).mandatory(false).employees(new HashSet<>()).build();
        nonExistingDepartment = Department.builder().id(null).name(DEPARTMENT_NON_EXISTING_NAME).build();

        lenient().when(mockDepartmentRepository.existsById(nonExistingDepartment.getId())).thenReturn(false);

        mockDepartment(existingDepartment_readOnly_mandatory);
        mockDepartment(existingDepartment);
    }

    public void mockDepartment(Department department) {
        lenient().when(mockDepartmentRepository.existsById(department.getId())).thenReturn(true);
        lenient().when(mockDepartmentRepository.findById(department.getId())).thenReturn(Optional.ofNullable(department));
        lenient().when(mockDepartmentRepository.save(department)).thenReturn(department);
    }

    @Test
    public void testUpdate_idNotPresent_failure() {
        assertThrows(NotFoundException.class, () -> departmentService.update(nonExistingDepartment));
    }

    @Test
    public void testUpdate_existingReadOnlyTrue_withNewReadOnlyFalse_success(){
        existingDepartment_readOnly_mandatory.setName(DEPARTMENT_NEW_NAME);
        existingDepartment_readOnly_mandatory.setReadOnly(false);
        Department department = departmentService.update(existingDepartment_readOnly_mandatory);
        assertEquals(existingDepartment_readOnly_mandatory.getName(), department.getName());
        assertEquals(existingDepartment_readOnly_mandatory.getId(), department.getId());
        assertEquals(existingDepartment_readOnly_mandatory.getReadOnly(), department.getReadOnly());
    }

    @Test
    public void testUpdate_existingReadOnlyTrue_withNewReadOnlyTrue_failure(){
        existingDepartment_readOnly_mandatory.setReadOnly(true);
        assertThrows(InvalidActionException.class, () -> departmentService.update(existingDepartment_readOnly_mandatory));
    }

//    @Test
//    public void testUpdate_existingReadOnlyFalse_withNewReadOnlyTrue_success(){
//        existingDepartment.setReadOnly(true);
//        Department department = departmentService.update(existingDepartment);
//          Existing values of 'existing dptt' will be seen as read_only true but should be read_only for existing. New values are required to be true
//        assertEquals(existingDepartment.getId(), department.getId());
//    }


    @Test
    public void testUpdate_existingReadOnlyFalse_withNewReadOnlyFalse_success(){
        Department department = departmentService.update(existingDepartment);
        assertEquals(existingDepartment.getId(), department.getId());
    }

    @Test
    public void testDelete_idNotPresent_failure() {
        assertThrows(NotFoundException.class, () -> departmentService.delete(nonExistingDepartment.getId()));
    }

    @Test
    public void testDelete_cannotDeleteReadOnlyTrueDepartment_success() {
        assertThrows(InvalidActionException.class, () -> departmentService.delete(existingDepartment_readOnly_mandatory.getId()));
    }

    @Test
    public void testDelete_deleteReadOnlyFalseDepartment_success() {
        departmentService.delete(existingDepartment.getId());
        //        What do I assert here? How to check?
    }

}
