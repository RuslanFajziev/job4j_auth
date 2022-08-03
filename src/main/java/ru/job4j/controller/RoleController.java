package ru.job4j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Role;
import ru.job4j.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {
    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class.getSimpleName());
    private final RoleRepository roleRep;

    public RoleController(final RoleRepository roleRep) {
        this.roleRep = roleRep;
    }

    @GetMapping("/")
    public List<Role> findAll() {
        LOG.info("Get All roles");
        List<Role> lstRole = new ArrayList<Role>();
        this.roleRep.findAll().forEach(lstRole::add);
        return lstRole;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        LOG.info("Role find by id={}", id);
        Optional<Role> person = this.roleRep.findById(id);
        return new ResponseEntity<Role>(
                person.orElse(new Role()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        LOG.info("Create role={}", role);
        return new ResponseEntity<Role>(
                this.roleRep.save(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Role role) {
        LOG.info("Update role={}", role);
        this.roleRep.save(role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        LOG.info(" Delete role by id={}", id);
        Optional<Role> roleOpt = this.roleRep.findById(id);
        if (roleOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Role role = new Role();
        role.setId(id);
        this.roleRep.delete(role);
        return ResponseEntity.ok().build();
    }
}