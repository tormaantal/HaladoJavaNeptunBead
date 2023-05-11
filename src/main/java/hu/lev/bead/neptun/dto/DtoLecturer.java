package hu.lev.bead.neptun.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DtoLecturer {
    private long id;
    private String name;
    private int age;
    private String email;
    private List<String> subjects;
}
