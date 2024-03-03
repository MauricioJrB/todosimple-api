package com.demo.todosimples.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.todosimples.models.Task;
import com.demo.todosimples.models.User;
import com.demo.todosimples.repositories.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(
                () -> new RuntimeException("Task not found! Id: " + id + ", Tipo: " + Task.class.getName()));
    }

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } 
        catch (Exception e) {
            throw new RuntimeException("It is not possible to delete because there are related entities!");
        }
    }
}
