package hu.lev.bead.neptun.dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class DtoStudent {
    private long id;
    private String name;
    private int age;
    private String neptunCode;
    private String email;
    private List<String> subjectList;
}
