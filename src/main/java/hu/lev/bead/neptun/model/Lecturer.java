package hu.lev.bead.neptun.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lecturer")
@Entity
public class Lecturer {

    @Id
    @GeneratedValue()
    private long id;
    @NotBlank
    private String name;
    @Min(1)
    @NotNull
    @Digits(integer = 2, fraction = 0)
    private int age;
    @Email
    @Pattern(regexp = "^(.+)@(.+)$")
    @NotBlank
    private String email;
//    Egy-több kapcsolat tárgyakkal
    @OneToMany(fetch = FetchType.LAZY)
    private List<Subject> subjects;
}
