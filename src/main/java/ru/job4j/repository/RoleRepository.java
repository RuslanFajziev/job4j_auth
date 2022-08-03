package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}