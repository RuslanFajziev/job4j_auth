package ru.job4j.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tittle;
    @Column(name = "bodymessage")
    private String bodyMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    public static Message of(String tittle, String bodyMessage, Room room, Person person) {
        Message message = new Message();
        message.tittle = tittle;
        message.bodyMessage = bodyMessage;
        message.room = room;
        message.person = person;
        return message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Message{"
                + "id=" + id
                + ", tittle='" + tittle + '\''
                + ", bodyMessage='" + bodyMessage + '\''
                + ", room=" + room
                + ", person=" + person
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return id == message.id && Objects.equals(tittle, message.tittle)
                && Objects.equals(bodyMessage, message.bodyMessage)
                && Objects.equals(room, message.room) && Objects.equals(person, message.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tittle, bodyMessage, room, person);
    }
}