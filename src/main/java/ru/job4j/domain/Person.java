package ru.job4j.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.lang.String getLogin() {
        return login;
    }

    public void setLogin(java.lang.String login) {
        this.login = login;
    }

    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        Person person = (Person) object;
        return id == person.id && java.util.Objects.equals(login, person.login)
                && java.util.Objects.equals(password, person.password);
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), id, login, password);
    }

    public java.lang.String toString() {
        return "Person{"
                + "id=" + id
                + ", login=" + login
                + ", password=" + password
                + '}';
    }
}