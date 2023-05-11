package hu.lev.bead.neptun.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class DtoSubject {
    private long id;
    private String name;
    private int credit;
    private String subjectCode;
    private String lecturer;
    private String room;
    private List<String> studentsList;
}
