package application.organization.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "EMPLOYEES")
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMPLOYEE")
    @SequenceGenerator(name = "SEQ_EMPLOYEE", sequenceName = "SEQ_EMPLOYEE", allocationSize = 1)
    private Long id;

    @ToString.Include
    @Column(nullable = false)
    private String nameFirst;

    @ToString.Include
    @Column(nullable = false)
    private String nameLast;

    @ToString.Include
    @ManyToMany
    @JoinTable(
            name = "MAP_EMPLOYEE_DEPARTMENTS",
            joinColumns = @JoinColumn(name = "ID_EMPLOYEE"),
            inverseJoinColumns = @JoinColumn(name = "ID_DEPARTMENT")
    )
    private Set<Department> departments;
}
