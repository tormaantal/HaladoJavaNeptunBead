package hu.lev.bead.neptun.service;

import hu.lev.bead.neptun.dto.DtoSubject;
import hu.lev.bead.neptun.exception.DefaultErrorException;
import hu.lev.bead.neptun.exception.InDatabaseException;
import hu.lev.bead.neptun.exception.NotFoundException;
import hu.lev.bead.neptun.model.Room;
import hu.lev.bead.neptun.model.Subject;
import hu.lev.bead.neptun.repository.RoomRepository;
import hu.lev.bead.neptun.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjetcService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private RoomRepository roomRepository;

//    Tárgy keresése ID alapján
    public DtoSubject findSubjectById(Long subjectId) {
        Subject subject = subjectRepository.findSubjectById(subjectId);
        if (subject == null) {
            throw new NotFoundException("No subject found!");
        } else {
            return convert(subject);
        }
    }

//    Összes tárgy kilistázása
    public List<DtoSubject> findAllSubject() {
        List<Subject> subjectList = subjectRepository.findAll();
        if (subjectList.isEmpty()) {
            throw new NotFoundException("No subject found!");
        } else {
            List<DtoSubject> dtoSubjectList = new ArrayList<>();
            subjectList.forEach(subject ->dtoSubjectList.add(convert(subject)));
            return dtoSubjectList;
        }
    }

//    Tárgyak keresése név alapján
    public List<DtoSubject> findSubjectByName(String name) {
        List<Subject> subjectList = subjectRepository.findSubjectByNameContainingIgnoreCase(name);
        if (subjectList.isEmpty()) {
            throw new NotFoundException("No subject found!");
        } else {
            List<DtoSubject> dtoSubjectList = new ArrayList<>();
            subjectList.forEach(subject -> dtoSubjectList.add(convert(subject)));
            return dtoSubjectList;
        }
    }

//    Tárgy létrehozása és módosítása
    public DtoSubject saveSubject(DtoSubject dtoSubject, long id) {
        if (id == -1) {
            if (subjectRepository.findSubjectBySubjectCode(dtoSubject.getSubjectCode()) != null) {
                throw new InDatabaseException(dtoSubject.getSubjectCode());
            }
            Subject subject = Subject.builder()
                    .name(dtoSubject.getName())
                    .credit(dtoSubject.getCredit())
                    .subjectCode(dtoSubject.getSubjectCode())
                    .build();
            Subject newSubject = subjectRepository.save(subject);
            return convert(newSubject);
        } else {
            Subject subject = subjectRepository.findSubjectById(id);
            subject.setName(dtoSubject.getName());
            subject.setCredit(dtoSubject.getCredit());
            subject.setSubjectCode(dtoSubject.getSubjectCode());
            Subject updatedSubject = subjectRepository.save(subject);
            return convert(updatedSubject);
        }
    }

//    Tárgy törlése
    public void deleteSubject(Long id) {
        findSubjectById(id);
        subjectRepository.deleteById(id);
    }

//    Tárgy hozzárendelése teremhez
    public DtoSubject assignRoomToSubject(long subjectId, long roomId) {
        Subject subject = subjectRepository.findSubjectById(subjectId);
        Room room = roomRepository.findRoomById(roomId);
        if (subject == null) {
            throw new NotFoundException("No subject found!");
        }
        if (room == null) {
            throw new NotFoundException("No room found!");
        }
        if (subject.getRoom() != null) {
            throw new DefaultErrorException(subject.getName() + " already assigned to room!");
        }
        subject.setRoom(room);
        subjectRepository.save(subject);
        room.getSubjects().add(subject);
        roomRepository.save(room);
        return convert(subject);
    }

//    Tárgy és terem hozzárendelés megszüntetése
    public DtoSubject cancelRoomFromSubject(long subjectId, long roomId) {
        Subject subject = subjectRepository.findSubjectById(subjectId);
        Room room = roomRepository.findRoomById(roomId);
        if (subject == null) {
            throw new NotFoundException("No subject found!");
        }
        if (room == null) {
            throw new NotFoundException("No room found!");
        }
        if (!subject.getRoom().getId().equals(room.getId())) {
            throw new DefaultErrorException(subject.getName() + " and " + room.getName() + "not connected!");
        }
        subject.setRoom(null);
        subjectRepository.save(subject);
        room.getSubjects().remove(subject);
        roomRepository.save(room);
        return convert(subject);
    }

//    DTO konvertálás
    public DtoSubject convert(Subject subject) {
        DtoSubject dtoSubject = DtoSubject.builder()
                .id(subject.getId())
                .name(subject.getName())
                .credit(subject.getCredit())
                .subjectCode(subject.getSubjectCode())
                .studentsList(new ArrayList<>())
                .build();
        if (subject.getLecturer() != null) {
            dtoSubject.setLecturer(subject.getLecturer().getName());
        }
        if (subject.getRoom() != null) {
            dtoSubject.setRoom(subject.getRoom().getName());
        }
        if (subject.getStudentsList() != null) {
            subject.getStudentsList().forEach(student -> dtoSubject.getStudentsList().add(student.getName()));
        }
        return dtoSubject;
    }

}
