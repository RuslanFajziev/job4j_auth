package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}