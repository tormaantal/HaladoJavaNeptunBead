package hu.lev.bead.neptun.repository;

import hu.lev.bead.neptun.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findSubjectByNameContainingIgnoreCase(String name);

    Subject findSubjectBySubjectCode(String code);

    Subject findSubjectById(long subjectId);
}
