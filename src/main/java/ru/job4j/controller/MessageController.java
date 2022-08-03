package ru.job4j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.domain.*;
import ru.job4j.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageController {
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class.getSimpleName());
    private final MessageRepository messageRep;
    private final RestTemplate rest;
    private static final String API_ROOM = "http://localhost:8080/room/";
    private static final String API_ROOM_ID = "http://localhost:8080/room/{id}";

    private static final String API_PERSON = "http://localhost:8080/person/";
    private static final String API_PERSON_ID = "http://localhost:8080/person/{id}";

    public MessageController(MessageRepository messageRep, RestTemplate rest) {
        this.messageRep = messageRep;
        this.rest = rest;
    }

    @GetMapping("/")
    public List<Message> findAll() {
        LOG.info("Get All message");
        List<Message> lstMessage = new ArrayList<Message>();
        this.messageRep.findAll().forEach(lstMessage::add);
        for (Message message : lstMessage) {
            message.setRoom(rest.getForObject(API_ROOM_ID, Room.class, message.getRoom().getId()));
            message.setPerson(rest.getForObject(API_PERSON_ID, Person.class, message.getPerson().getId()));
        }
        return lstMessage;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        LOG.info("Message find by id={}", id);
        Optional<Message> message = this.messageRep.findById(id);
        message.ifPresent(value -> {
            value.setRoom(rest.getForObject(API_ROOM_ID, Room.class, value.getRoom().getId()));
            value.setPerson(rest.getForObject(API_PERSON_ID, Person.class, value.getPerson().getId()));
        });
        return new ResponseEntity<Message>(
                message.orElse(new Message()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody Message message) {
        LOG.info("Create message={}", message);
        Room room = rest.postForObject(API_ROOM, message.getRoom(), Room.class);
        Person person = rest.postForObject(API_PERSON, message.getPerson(), Person.class);
        message.setRoom(room);
        message.setPerson(person);
        return new ResponseEntity<Message>(
                this.messageRep.save(message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        LOG.info("Update message={}", message);
        Room room = rest.postForObject(API_ROOM, message.getRoom(), Room.class);
        Person person = rest.postForObject(API_PERSON, message.getPerson(), Person.class);
        message.setRoom(room);
        message.setPerson(person);
        this.messageRep.save(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        LOG.info(" Delete message by id={}", id);
        Optional<Message> messageOpt = this.messageRep.findById(id);
        if (messageOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Message message = new Message();
        message.setId(id);
        this.messageRep.delete(message);
        return ResponseEntity.
                ok().build();
    }
}