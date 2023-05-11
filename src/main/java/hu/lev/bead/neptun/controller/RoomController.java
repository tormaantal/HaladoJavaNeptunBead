package hu.lev.bead.neptun.controller;

import hu.lev.bead.neptun.dto.DtoRoom;
import hu.lev.bead.neptun.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    public RoomService roomService;

    @GetMapping("/")
    public List<DtoRoom> findAllRoom() {
        return roomService.findAllRoom();
    }

    @GetMapping("/{id}")
    public DtoRoom findRoomByName(@PathVariable long id) {
        return roomService.findRoomById(id);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public DtoRoom addRoom(@Valid @RequestBody DtoRoom dtoRoom) {
        return roomService.saveRoom(dtoRoom, -1);
    }

    @PutMapping(value = "/edit/{id}", consumes = "application/json")
    public DtoRoom editRoom(@Valid @RequestBody DtoRoom newRoom, @PathVariable("id") long id) {
        DtoRoom oldRoom = roomService.findRoomById(id);
        return roomService.saveRoom(newRoom, oldRoom.getId());
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable long id) {
        roomService.deleteRoom(id);
        return "Room deleted!";
    }
}
