package ru.job4j.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
public class EmployeeDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null")
    @Min(value = 1, message = "Id should not be less than 1")
    private int id;
    @NotBlank(message = "Name must be not empty")
    private String name;

    @NotBlank(message = "Surname must be not empty")
    private String surname;

    public static EmployeeDTO of(String name, String surname) {
        EmployeeDTO employee = new EmployeeDTO();
        employee.name = name;
        employee.surname = surname;
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeeDTO employee = (EmployeeDTO) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + '}';
    }
}