package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Employee;
import ru.job4j.domain.EmployeeDTO;
import ru.job4j.repository.EmployeeRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class.getSimpleName());
    private final EmployeeRepository employeeRep;

    private final ObjectMapper objectMapper;

    public EmployeeController(EmployeeRepository employeeRep, ObjectMapper objectMapper) {
        this.employeeRep = employeeRep;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        LOG.info("Get All employees");
        List<Employee> lstEmployee = new ArrayList<Employee>();
        this.employeeRep.findAll().forEach(lstEmployee::add);
        return lstEmployee;
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable int id) {
        Optional<Employee> employee = this.employeeRep.findById(id);
        if (employee.isPresent()) {
            LOG.info("Employee find by id={}", id);
        } else {
            LOG.info("Employee find by id={} is not found, check requisites.", id);
        }
        return employee.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Employee is not found. Please, check requisites."
        ));
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        if (employee.getName() == null || employee.getSurname() == null || employee.getInn() == null) {
            throw new NullPointerException("Employee, name or surname, or inn mustn't be empty");
        }
        if (employee.getInn().length() < 6) {
            throw new IllegalArgumentException("Invalid inn length < 6");
        }
        LOG.info("Create employee={}", employee);
        return new ResponseEntity<Employee>(
                this.employeeRep.save(employee),
                HttpStatus.CREATED
        );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", "Invalid inn");
                put("details", e.getMessage());
                put("type", e.getClass());
            }
        }));
        LOG.error(e.getLocalizedMessage());
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        LOG.info("Update employee={}", employee);
        this.employeeRep.save(employee);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public Employee patch(@RequestBody EmployeeDTO employeeDTO) {
        var employee = this.employeeRep.findById(employeeDTO.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        LOG.info("Patch employee={}", employee);
        return this.employeeRep.save(employee);
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