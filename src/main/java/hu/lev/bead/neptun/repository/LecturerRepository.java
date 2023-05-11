package hu.lev.bead.neptun.repository;

import hu.lev.bead.neptun.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    Lecturer findLecturerById(long id);

    List<Lecturer> findByNameContainingIgnoreCase(String name);
}
