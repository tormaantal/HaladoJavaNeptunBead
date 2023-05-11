package hu.lev.bead.neptun.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private long id;
    @NotBlank
    private String name;
    @Min(18)
    @NotNull
    @Digits(integer = 2, fraction = 0)
    private int age;
    @Column(length = 6, unique = true,nullable = false)
    private String neptunCode;
    @Email
    private String email;
//    Több-több kapcsolat tárgyakkal
    @ManyToMany
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjectList;
}
