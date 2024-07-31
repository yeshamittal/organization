package application.organization.repositories;

import application.organization.entities.Department;
import application.organization.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDepartmentRepository extends JpaRepository<Employee, Department>{
}
