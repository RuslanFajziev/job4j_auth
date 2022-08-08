package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.domain.Login;

import java.util.Optional;

public interface LoginRepository extends CrudRepository<Login, Integer> {
    Optional<Login> findByUsername(String username);
}