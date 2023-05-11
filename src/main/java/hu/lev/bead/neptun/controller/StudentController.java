package hu.lev.bead.neptun.controller;

import hu.lev.bead.neptun.dto.DtoStudent;
import hu.lev.bead.neptun.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public List<DtoStudent> findAllStudent() {

        return studentService.findAllStudent();
    }

    @GetMapping("/{name}")
    public List<DtoStudent> findStudentByName(@PathVariable String name) {
        return studentService.findStudentByName(name);
    }

    @GetMapping("/id/{id}")
    public DtoStudent findStudentByNeptunCode(@PathVariable long id) {
        return studentService.findStudentById(id);
    }

    @PostMapping("/add")
    public DtoStudent addStudent(@Valid @RequestBody DtoStudent student) {
        return studentService.saveStudent(student, -1);
    }

    @PutMapping(value = "/edit/{id}", consumes = "application/json")
    public DtoStudent editStudent(@Valid @RequestBody DtoStudent newStudent, @PathVariable long id) {
        DtoStudent oldStudent = studentService.findStudentById(id);
        return studentService.saveStudent(newStudent, oldStudent.getId());
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return "Student deleted!";
    }

    @PutMapping("/{studentId}/assigne/{subjectId}")
    public DtoStudent assignSubjectToLecturer(@PathVariable long studentId, @PathVariable long subjectId) {
        return studentService.assignSubjectToStudent(studentId, subjectId);
    }

    @PutMapping("/{studentId}/cancel/{subjectId}")
    public DtoStudent cancelSubjectToLecturer(@PathVariable long studentId, @PathVariable long subjectId) {
        return studentService.cancelSubjectToStudent(studentId, subjectId);
    }

}
