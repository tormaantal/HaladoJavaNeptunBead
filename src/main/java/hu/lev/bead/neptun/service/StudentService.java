package hu.lev.bead.neptun.service;

import hu.lev.bead.neptun.dto.DtoStudent;
import hu.lev.bead.neptun.exception.DefaultErrorException;
import hu.lev.bead.neptun.exception.InDatabaseException;
import hu.lev.bead.neptun.exception.NotFoundException;
import hu.lev.bead.neptun.model.Student;
import hu.lev.bead.neptun.model.Subject;
import hu.lev.bead.neptun.repository.StudentRepository;
import hu.lev.bead.neptun.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

//    Diákok listázása név alapján
    public List<DtoStudent> findStudentByName(String name) {
        List<Student> studentList = studentRepository.findStudentByNameContainingIgnoreCase(name);
        if (studentList.isEmpty()) {
            throw new NotFoundException("No student found!");
        } else {
            List<DtoStudent> dtoStudentList = new ArrayList<>();
            studentList.forEach(student -> dtoStudentList.add(convert(student)));
            return dtoStudentList;
        }
    }

//    Diák keresése ID alapján
    public DtoStudent findStudentById(long id) {
        Student student = studentRepository.findStudentById(id);
        if (student == null) {
            throw new NotFoundException("No student found!");
        } else {
            return convert(student);
        }
    }

//    Összes diák listázása
    public List<DtoStudent> findAllStudent() {
        List<Student> studentList = studentRepository.findAll();
        if (studentList.isEmpty()) {
            throw new NotFoundException("No student found!");
        } else {
            List<DtoStudent> dtoStudentList = new ArrayList<>();
            studentList.forEach(student -> dtoStudentList.add(convert(student)));
            return dtoStudentList;
        }
    }

//    Diák létrehozása vagy módosítása
    public DtoStudent saveStudent(DtoStudent dtoStudent, long id) {
        if (id == -1) {
            if (studentRepository.findStudentByNeptunCodeIgnoreCase(dtoStudent.getNeptunCode()) != null) {
                throw new InDatabaseException(dtoStudent.getNeptunCode());
            }
            Student student = Student.builder()
                    .name(dtoStudent.getName())
                    .age(dtoStudent.getAge())
                    .email(dtoStudent.getEmail())
                    .neptunCode(dtoStudent.getNeptunCode())
                    .build();
            Student newStudent = studentRepository.save(student);
            return convert(newStudent);
        } else {
            Student student = studentRepository.findStudentById(id);
            student.setName(dtoStudent.getName());
            student.setAge(dtoStudent.getAge());
            student.setEmail(dtoStudent.getEmail());
            Student updatedLecturer = studentRepository.save(student);
            return convert(updatedLecturer);
        }
    }

//    Diák törlése
    public void deleteStudent(long id) {
        findStudentById(id);
        studentRepository.deleteById(id);
    }

//    Diákhoz rendel egy tárgyat
    public DtoStudent assignSubjectToStudent(long studentId, long subjectId) {
        Student student = studentRepository.findStudentById(studentId);
        Subject subject = subjectRepository.findSubjectById(subjectId);
        if (student == null) {
            throw new NotFoundException("No student found!");
        }
        if (subject == null) {
            throw new NotFoundException("No subject found!");
        }
        if(student.getSubjectList().contains(subject)){
            throw new DefaultErrorException(student.getName() + " already assigned to this subject!");
        }
        student.getSubjectList().add(subject);
        subject.getStudentsList().add(student);
        studentRepository.save(student);
        subjectRepository.save(subject);
        return convert(student);
    }

//    Diák és egy tárgy kapcsolatát megszünteti
    public DtoStudent cancelSubjectToStudent(long studentId, long subjectId) {
        Student student = studentRepository.findStudentById(studentId);
        Subject subject = subjectRepository.findSubjectById(subjectId);
        if (student == null) {
            throw new NotFoundException("No student found!");
        }
        if (subject == null) {
            throw new NotFoundException("No subject found!");
        }
        if (!student.getSubjectList().contains(subject)){
            throw new DefaultErrorException(student.getName() + " not assigned to " + subject.getName());
        }
        student.getSubjectList().remove(subject);
        subject.getStudentsList().remove(student);
        studentRepository.save(student);
        subjectRepository.save(subject);
        return convert(student);
    }

//    DTO konvertálás
    public DtoStudent convert(Student student) {
        DtoStudent dtoStudent = DtoStudent.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .neptunCode(student.getNeptunCode())
                .email(student.getEmail())
                .subjectList(new ArrayList<>())
                .build();
        if (student.getSubjectList() != null){
            student.getSubjectList().forEach(subject -> dtoStudent.getSubjectList().add(subject.getName()));
        }
        return dtoStudent;
    }
}
