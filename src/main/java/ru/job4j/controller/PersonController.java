package ru.job4j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.domain.Employee;
import ru.job4j.domain.Person;
import ru.job4j.domain.Role;
import ru.job4j.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class.getSimpleName());
    private final PersonRepository personRep;
    private final RestTemplate rest;
    private static final String API_EMPLOYEE = "http://localhost:8080/employee/";
    private static final String API_EMPLOYEE_ID = "http://localhost:8080/employee/{id}";

    private static final String API_ROLE = "http://localhost:8080/role/";
    private static final String API_ROLE_ID = "http://localhost:8080/role/{id}";

    public PersonController(PersonRepository personRep, RestTemplate rest) {
        this.personRep = personRep;
        this.rest = rest;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        LOG.info("Get All persons");
        List<Person> lstPerson = new ArrayList<Person>();
        this.personRep.findAll().forEach(lstPerson::add);
        for (Person person : lstPerson) {
            person.setEmployee(rest.getForObject(API_EMPLOYEE_ID, Employee.class, person.getEmployee().getId()));
            person.setRole(rest.getForObject(API_ROLE_ID, Role.class, person.getRole().getId()));
        }
        return lstPerson;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        LOG.info("Person find by id={}", id);
        Optional<Person> person = this.personRep.findById(id);
        person.ifPresent(value -> {
            value.setEmployee(rest.getForObject(API_EMPLOYEE_ID, Employee.class, value.getEmployee().getId()));
            value.setRole(rest.getForObject(API_ROLE_ID, Role.class, value.getRole().getId()));
        });
        return new ResponseEntity<Person>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        LOG.info("Create person={}", person);
        Employee employee = rest.postForObject(API_EMPLOYEE, person.getEmployee(), Employee.class);
        Role role = rest.postForObject(API_ROLE, person.getRole(), Role.class);
        person.setEmployee(employee);
        person.setRole(role);
        return new ResponseEntity<Person>(
                this.personRep.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        LOG.info("Update person={}", person);
        Employee employee = rest.postForObject(API_EMPLOYEE, person.getEmployee(), Employee.class);
        Role role = rest.postForObject(API_ROLE, person.getRole(), Role.class);
        person.setEmployee(employee);
        person.setRole(role);
        this.personRep.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        LOG.info(" Delete person by id={}", id);
        Optional<Person> personOpt = this.personRep.findById(id);
        if (personOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Person person = new Person();
        person.setId(id);
        this.personRep.delete(person);
        return ResponseEntity.
                ok().build();
    }
}