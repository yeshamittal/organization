package application.organization.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DEPARTMENTS")
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
    @SequenceGenerator(name = "department_seq", sequenceName = "DEPARTMENT_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean read_only = false;

    @Column(nullable = false)
    private Boolean mandatory = false;

    public Department(String name) {
        this.name = name;
    }

    protected Department() {}

    @Override
    public String toString() {
        return String.format(
                "Department[id=%d, name='%s', read_only='%s', mandatory='%s']",
                id, name, read_only, mandatory);
    }
}
