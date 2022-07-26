package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}