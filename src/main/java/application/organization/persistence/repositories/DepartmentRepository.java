package application.organization.persistence.repositories;

import application.organization.persistence.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findDepartmentsByMandatoryTrue();
}

