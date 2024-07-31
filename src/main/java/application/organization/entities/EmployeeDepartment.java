package application.organization.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "EMPLOYEE_DEPARTMENTS")
public class EmployeeDepartment {
    @Id
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;
}
