package application.organization.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "EMPLOYEES")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "EMPLOYEE_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @ManyToMany
    @JoinTable(
            name = "EMPLOYEE_DEPARTMENTS",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "DEPARTMENT_ID")
    )
    private Set<Department> departments;

    public Employee(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Employee() {}

    @Override
    public String toString() {
        return String.format(
                "Employee[firstName='%s', lastName='%s', departments=%s]",
                first_name, last_name, departments);
    }
}
