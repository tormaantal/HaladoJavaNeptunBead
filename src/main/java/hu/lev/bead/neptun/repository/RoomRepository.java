package hu.lev.bead.neptun.repository;

import hu.lev.bead.neptun.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findRoomByNameContainingIgnoreCase(String name);

    Room findRoomById(Long id);
}
