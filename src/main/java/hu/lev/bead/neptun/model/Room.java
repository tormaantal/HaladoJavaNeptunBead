package hu.lev.bead.neptun.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String building;
    @NotNull
    @Digits(integer = 3, fraction = 0)
    private int seats;
//    Egy-több kapcsolat tárgyakkal
    @OneToMany(fetch = FetchType.LAZY)
    private List<Subject> subjects;
}



