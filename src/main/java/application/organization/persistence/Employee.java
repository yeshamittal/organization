package application.organization.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "EMPLOYEES")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMPLOYEE")
    @SequenceGenerator(name = "SEQ_EMPLOYEE", sequenceName = "SEQ_EMPLOYEE", allocationSize = 1)
    private Long id;

    @ToString.Include
    @NotNull(message = "First name cannot be null")
    @Column(nullable = false)
    private String nameFirst;

    @ToString.Include
    @NotNull(message = "Last name cannot be null")
    @Column(nullable = false)
    private String nameLast;

    @ToString.Include
    @NotNull(message = "Departments name cannot be null")
    @ManyToMany
    @JoinTable(
            name = "MAP_EMPLOYEE_DEPARTMENTS",
            joinColumns = @JoinColumn(name = "ID_EMPLOYEE"),
            inverseJoinColumns = @JoinColumn(name = "ID_DEPARTMENT")
    )
    private Set<Department> departments;
}
