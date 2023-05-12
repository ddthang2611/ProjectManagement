package com.project.repository;

import com.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password);
    Optional<User> getUserByUsername(String username);

    void deleteById(int userId);
    Optional<User> findById(int userId);
    List<User> findAll();

}

