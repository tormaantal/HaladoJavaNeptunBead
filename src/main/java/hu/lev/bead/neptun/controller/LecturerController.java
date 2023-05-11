package hu.lev.bead.neptun.controller;

import hu.lev.bead.neptun.dto.DtoLecturer;
import hu.lev.bead.neptun.service.LecturerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecturer")
public class LecturerController {

    private LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @GetMapping("/")
    public List<DtoLecturer> findAllLecturer() {
        return lecturerService.findAllLecturer();
    }

    @GetMapping("/{name}")
    public List<DtoLecturer> findLecturerByName(@PathVariable String name) {
        return lecturerService.findLecturerByName(name);
    }

    @GetMapping("/id/{id}")
    public DtoLecturer findLecturerById(@PathVariable long id) {
        return lecturerService.findLecturerById(id);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public DtoLecturer addLecturer(@Valid @RequestBody DtoLecturer dtoLecturer) {
        return lecturerService.saveLecturer(dtoLecturer, -1);
    }

    @PutMapping(value = "/edit/{id}", consumes = "application/json")
    public DtoLecturer editLecturer(@RequestBody DtoLecturer newLecturer, @PathVariable("id") long id) {
        DtoLecturer oldLecturer = lecturerService.findLecturerById(id);
        return lecturerService.saveLecturer(newLecturer, oldLecturer.getId());
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLecturer(@PathVariable long id) {
        lecturerService.deleteLecturer(id);
        return "Lecturer deleted!";
    }

    @PutMapping("/{lecturerId}/assigne/{subjectId}")
    public DtoLecturer assignSubjectToLecturer(@PathVariable long lecturerId, @PathVariable long subjectId) {
        return lecturerService.assignSubjectToLecturer(lecturerId, subjectId);
    }

    @PutMapping("/{lecturerId}/cancel/{subjectId}")
    public DtoLecturer cancelSubjectFromLecturer(@PathVariable long lecturerId, @PathVariable long subjectId) {
        return lecturerService.cancelSubjectFromLecturer(lecturerId, subjectId);
    }
}
