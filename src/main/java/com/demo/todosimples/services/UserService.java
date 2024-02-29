package com.demo.todosimples.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.todosimples.models.User;
import com.demo.todosimples.repositories.TaskRepository;
import com.demo.todosimples.repositories.UserRepository;



@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User findById(Long id) { 
        @SuppressWarnings("null")
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    @SuppressWarnings("null")
    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    @SuppressWarnings("null")
    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new RuntimeException("It is not possible to delete because there are related entities!");
        }
    }
}