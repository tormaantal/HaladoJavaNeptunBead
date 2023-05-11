package hu.lev.bead.neptun.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subject")
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String name;
    @Min(1)
    @Max(5)
    @NotNull
    @Digits(integer = 1, fraction = 0)
    private int credit;
    @Column(unique = true,nullable = false)
    private String subjectCode;
//    Több-egy kapcsolat tárgyakkal
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;
//    Több-egy kapcsolat előadókkal
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
//    Több-több kapcsolat diákokkal
    @ManyToMany(mappedBy = "subjectList")
    private List<Student> studentsList;
}
