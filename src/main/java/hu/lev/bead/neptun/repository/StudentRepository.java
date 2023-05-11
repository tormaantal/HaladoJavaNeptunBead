package hu.lev.bead.neptun.repository;

import hu.lev.bead.neptun.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentByNeptunCodeIgnoreCase(String neptunCode);

    Student findStudentById(long id);
    List<Student> findStudentByNameContainingIgnoreCase(String name);
}
