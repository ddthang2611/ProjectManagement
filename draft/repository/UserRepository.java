package com.sapo.edu.demo.repository;

import com.sapo.edu.demo.entity.User;

import java.sql.SQLException;

public interface UserRepository {
    public User insecureSQLinjectionLogin(User user);
    public User secureSQLinjectionLogin(User user) throws SQLException;
    public User getUserByUsername(String username);
}
