package hu.lev.bead.neptun.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class DtoRoom {
    private Long id;
    private String name;
    private String building;
    private int seats;
    private List<String> subjects;
}
