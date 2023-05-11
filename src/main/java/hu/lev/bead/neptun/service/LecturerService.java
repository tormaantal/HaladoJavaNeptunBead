package hu.lev.bead.neptun.service;

import hu.lev.bead.neptun.dto.DtoLecturer;
import hu.lev.bead.neptun.exception.DefaultErrorException;
import hu.lev.bead.neptun.exception.NotFoundException;
import hu.lev.bead.neptun.model.Lecturer;
import hu.lev.bead.neptun.model.Subject;
import hu.lev.bead.neptun.repository.LecturerRepository;
import hu.lev.bead.neptun.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private SubjectRepository subjectRepository;

//    Összes tanár listázása
    public List<DtoLecturer> findAllLecturer() {
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        if (lecturerList.isEmpty()) {
            throw new NotFoundException("No lecturer found!");
        } else {
            List<DtoLecturer> dtoLecturers = new ArrayList<>();
            lecturerList.forEach(lecturer -> dtoLecturers.add(convert(lecturer)));
            return dtoLecturers;
        }
    }

//    Tanár keresése név alapján
    public List<DtoLecturer> findLecturerByName(String name) {
        List<Lecturer> lecturerList = lecturerRepository.findByNameContainingIgnoreCase(name);
        if (lecturerList.isEmpty()) {
            throw new NotFoundException("No lecturer found!");
        } else {
            List<DtoLecturer> dtoLecturers = new ArrayList<>();
            lecturerList.forEach(lecturer -> dtoLecturers.add(convert(lecturer)));
            return dtoLecturers;
        }
    }

//    Tanár keresése ID alapján
    public DtoLecturer findLecturerById(long id) {
        Lecturer lecturer = lecturerRepository.findLecturerById(id);
        if (lecturer == null) {
            throw new NotFoundException("No lecturer found!");
        } else {
            return convert(lecturer);
        }
    }

//    Tanár létrehozása vagy módosítása
    public DtoLecturer saveLecturer(DtoLecturer dtoLecturer, long id) {
        if (id == -1) {
            Lecturer lecturer = Lecturer.builder()
                    .name(dtoLecturer.getName())
                    .age(dtoLecturer.getAge())
                    .email(dtoLecturer.getEmail())
                    .build();
            Lecturer newLecturer = lecturerRepository.save(lecturer);
            return convert(newLecturer);
        } else {
            Lecturer lecturer = lecturerRepository.findLecturerById(id);
            lecturer.setName(dtoLecturer.getName());
            lecturer.setAge(dtoLecturer.getAge());
            lecturer.setEmail(dtoLecturer.getEmail());
            Lecturer updatedLecturer = lecturerRepository.save(lecturer);
            return convert(updatedLecturer);
        }
    }

//    Tanár törlése
    public void deleteLecturer(long id) {
        findLecturerById(id);
        lecturerRepository.deleteById(id);
    }

//    Tanárhoz hozzárendel egy tárgyat
    public DtoLecturer assignSubjectToLecturer(Long lecturerId, Long subjectId) {
        Lecturer lecturer = lecturerRepository.findLecturerById(lecturerId);
        Subject subject = subjectRepository.findSubjectById(subjectId);
        if (lecturer == null) {
            throw new NotFoundException("No lecturer found!");
        }
        if (subject == null) {
            throw new NotFoundException("No subject found!");
        }
        if (lecturer.getSubjects().contains(subject)){
            throw new DefaultErrorException(lecturer.getName() + " already assigned this subject!");
        }
        lecturer.getSubjects().add(subject);
        lecturerRepository.save(lecturer);
        subject.setLecturer(lecturer);
        subjectRepository.save(subject);
        return convert(lecturer);
    }

//    Tanár és egy tárgy kapcsolatának megszüntetése
    public DtoLecturer cancelSubjectFromLecturer(Long lecturerId, Long subjectId) {
        Lecturer lecturer = lecturerRepository.findLecturerById(lecturerId);
        Subject subject = subjectRepository.findSubjectById(subjectId);
        if (lecturer == null) {
            throw new NotFoundException("No lecturer found!");
        }
        if (subject == null) {
            throw new NotFoundException("No subject found!");
        }
        if (!lecturer.getSubjects().contains(subject)){
            throw new DefaultErrorException(lecturer.getName() + " not assigned to " + subject.getName());
        }
        lecturer.getSubjects().remove(subject);
        lecturerRepository.save(lecturer);
        subject.setLecturer(null);
        subjectRepository.save(subject);
        return convert(lecturer);
    }

//    DTO konvertálás
    private DtoLecturer convert(Lecturer lecturer) {
        DtoLecturer dtoLecturer = DtoLecturer.builder()
                .id(lecturer.getId())
                .name(lecturer.getName())
                .age(lecturer.getAge())
                .email(lecturer.getEmail())
                .subjects(new ArrayList<>())
                .build();
        if (lecturer.getSubjects() != null) {
            lecturer.getSubjects().forEach(subject -> dtoLecturer.getSubjects().add(subject.getName()));
        }
        return dtoLecturer;
    }
}

