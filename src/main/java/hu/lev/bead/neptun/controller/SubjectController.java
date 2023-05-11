package hu.lev.bead.neptun.controller;

import hu.lev.bead.neptun.dto.DtoSubject;
import hu.lev.bead.neptun.service.SubjetcService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjetcService subjetcService;

    @GetMapping("/")
    public List<DtoSubject> findAllSubject() {
        return subjetcService.findAllSubject();
    }

    @GetMapping("/{name}")
    public List<DtoSubject> findSubjectByName(@PathVariable String name) {
        return subjetcService.findSubjectByName(name);
    }

    @GetMapping("/id/{subjectId}")
    public DtoSubject findSubjectById(@PathVariable long subjectId) {
        return subjetcService.findSubjectById(subjectId);
    }

    @PostMapping("/add")
    public DtoSubject addSubject(@Valid @RequestBody DtoSubject subject) {
        return subjetcService.saveSubject(subject, -1);
    }

    @PutMapping(value = "/edit/{subjectId}", consumes = "application/json")
    public DtoSubject editSubject(@Valid @RequestBody DtoSubject newSubject, @PathVariable long subjectId) {
        DtoSubject oldSubject = subjetcService.findSubjectById(subjectId);
        return subjetcService.saveSubject(newSubject, oldSubject.getId());
    }

    @DeleteMapping("/delete/{subjectId}")
    public String deleteSubject(@PathVariable long subjectId) {
        subjetcService.deleteSubject(subjectId);
        return "Subject deleted!";
    }

    @PutMapping("/{subjectId}/assigne/{roomId}")
    public DtoSubject assigneRoomToSubject(@PathVariable long subjectId, @PathVariable long roomId) {
        return subjetcService.assignRoomToSubject(subjectId, roomId);
    }

    @PutMapping("/{subjectId}/cancel/{roomId}")
    public DtoSubject cancelRoomFromSubject(@PathVariable long subjectId, @PathVariable long roomId) {
        return subjetcService.cancelRoomFromSubject(subjectId, roomId);
    }

}
