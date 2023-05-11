package hu.lev.bead.neptun.service;

import hu.lev.bead.neptun.dto.DtoRoom;
import hu.lev.bead.neptun.exception.InDatabaseException;
import hu.lev.bead.neptun.exception.NotFoundException;
import hu.lev.bead.neptun.model.Room;
import hu.lev.bead.neptun.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    public RoomRepository roomRepository;

//    Összes előadó listázása
    public List<DtoRoom> findAllRoom() {
        List<Room> roomList = roomRepository.findAll();
        if (roomList.isEmpty()) {
            throw new NotFoundException("No room found!");
        } else {
            List<DtoRoom> dtoRoomList = new ArrayList<>();
            roomList.forEach(room -> dtoRoomList.add(convert(room)));
            return dtoRoomList;
        }
    }

//    Előadó keresése ID alapján
    public DtoRoom findRoomById(Long id) {
        Room room = roomRepository.findRoomById(id);
        if (room == null) {
            throw new NotFoundException("No room found!");
        } else {
            return convert(room);
        }
    }

//    Előadó létrehozása vagy módosítása
    public DtoRoom saveRoom(DtoRoom dtoRoom, long id) {
        if (id == -1) {
            if (roomRepository.findRoomByNameContainingIgnoreCase(dtoRoom.getName()).contains(dtoRoom)) {
                throw new InDatabaseException(dtoRoom.getName());
            }
            Room room = Room.builder()
                    .name(dtoRoom.getName())
                    .building(dtoRoom.getBuilding())
                    .seats(dtoRoom.getSeats())
                    .build();
            Room newRoom = roomRepository.save(room);
            return convert(newRoom);
        } else {
            Room room = roomRepository.findRoomById(id);
            room.setName(dtoRoom.getName());
            room.setBuilding(dtoRoom.getBuilding());
            room.setSeats(dtoRoom.getSeats());
            Room updateRoom = roomRepository.save(room);
            return convert(updateRoom);
        }
    }

//    Előadó törlése
    public void deleteRoom(Long id) {
        findRoomById(id);
        roomRepository.deleteById(id);
    }

//    DTO konvertálás
    public DtoRoom convert(Room room) {
        DtoRoom dtoRoom = DtoRoom.builder()
                .id(room.getId())
                .name(room.getName())
                .building(room.getBuilding())
                .seats(room.getSeats())
                .subjects(new ArrayList<>())
                .build();
        if (room.getSubjects() != null){
            room.getSubjects().forEach(subject -> dtoRoom.getSubjects().add(subject.getName()));
        }
        return dtoRoom;
    }
}
