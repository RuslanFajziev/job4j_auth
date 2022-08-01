package ru.job4j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Employee;
import ru.job4j.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class.getSimpleName());
    private final EmployeeRepository employeeRep;

    public EmployeeController(final EmployeeRepository employeeRep) {
        this.employeeRep = employeeRep;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        LOG.info("Get All employees");
        List<Employee> lstEmployee = new ArrayList<Employee>();
        this.employeeRep.findAll().forEach(lstEmployee::add);
        return lstEmployee;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) {
        LOG.info("Employee find by id={}", id);
        Optional<Employee> person = this.employeeRep.findById(id);
        return new ResponseEntity<Employee>(
                person.orElse(new Employee()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        LOG.info("Create employee={}", employee);
        return new ResponseEntity<Employee>(
                this.employeeRep.save(employee),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        LOG.info("Update employee={}", employee);
        this.employeeRep.save(employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        LOG.info(" Delete employee by id={}", id);
        Optional<Employee> employeeOpt = this.employeeRep.findById(id);
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Employee employee = new Employee();
        employee.setId(id);
        this.employeeRep.delete(employee);
        return ResponseEntity.ok().build();
    }
}