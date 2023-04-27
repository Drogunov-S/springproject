package ru.drogunov.springproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.drogunov.springproject.dao.TaskDao;
import ru.drogunov.springproject.model.entity.Task;

@Service
@Transactional(readOnly = true)
public class TaskService {
    private final TaskDao taskDao;

    @Autowired
    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public Page<Task> getAll(Pageable page) {
        Page<Task> all = taskDao.findAll(page);
        return all;
    }

    @Transactional
    public void delete(Long id) {
        taskDao.deleteById(id);
    }

    public void update(Task task) {
        taskDao.save(task);
    }
}
