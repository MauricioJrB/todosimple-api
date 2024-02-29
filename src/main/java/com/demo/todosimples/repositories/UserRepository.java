package com.demo.todosimples.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.todosimples.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
