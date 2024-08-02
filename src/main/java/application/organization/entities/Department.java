package application.organization.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "DEPARTMENTS")
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"employees"})
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEPARTMENT")
    @SequenceGenerator(name = "SEQ_DEPARTMENT", sequenceName = "SEQ_DEPARTMENT", allocationSize = 1)
    private Long id;

    @ToString.Include
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean readOnly = false;

    @Column(nullable = false)
    private Boolean mandatory = false;

    @ToString.Include
    @ManyToMany(mappedBy = "departments")
    private Set<Employee> employees;
}
