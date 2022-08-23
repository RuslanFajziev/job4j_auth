package ru.job4j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Room;
import ru.job4j.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    private static final Logger LOG = LoggerFactory.getLogger(RoomController.class.getSimpleName());
    private final RoomRepository roomRep;

    public RoomController(final RoomRepository roomRep) {
        this.roomRep = roomRep;
    }

    @GetMapping("/")
    public List<Room> findAll() {
        LOG.info("Get All rooms");
        List<Room> lstRoom = new ArrayList<Room>();
        this.roomRep.findAll().forEach(lstRoom::add);
        return lstRoom;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        LOG.info("Room find by id={}", id);
        Optional<Room> person = this.roomRep.findById(id);
        return new ResponseEntity<Room>(
                person.orElse(new Room()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        LOG.info("Create room={}", room);
        return new ResponseEntity<Room>(
                this.roomRep.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        LOG.info("Update room={}", room);
        this.roomRep.save(room);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public ResponseEntity<Room> patch(@RequestBody Map<String, String> body) {
        int idBody = Integer.valueOf(body.get("id"));
        Optional<Room> roomOpt = this.roomRep.findById(idBody);
        if (roomOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var room = Room.of(body.get("name"));
        room.setId(idBody);
        LOG.info("Patch room={}", room);
        this.roomRep.save(room);
        return new ResponseEntity<Room>(
                this.roomRep.save(room),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        LOG.info(" Delete room by id={}", id);
        Optional<Room> roomOpt = this.roomRep.findById(id);
        if (roomOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Room room = new Room();
        room.setId(id);
        this.roomRep.delete(room);
        return ResponseEntity.ok().build();
    }
}