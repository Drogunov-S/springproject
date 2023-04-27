package ru.drogunov.springproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.drogunov.springproject.model.entity.Task;

@Repository
public interface TaskDao extends JpaRepository<Task, Long> {
}
