package application.organization.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    public Employee(String first_name, String lname) {
        this.first_name = first_name;
        this.last_name = lname;
    }

    public Employee() {}

    @Override
    public String toString() {
        return String.format(
                "Employee[firstName='%s', lastName='%s']",
                first_name, last_name);
    }
}
