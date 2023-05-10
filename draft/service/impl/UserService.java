package com.sapo.edu.demo.service.impl;

import com.sapo.edu.demo.entity.User;
import com.sapo.edu.demo.repository.UserRepository;
import com.sapo.edu.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean checkLogin(User user) {
        User userFromDB = null;
        try {
            userFromDB = userRepository.secureSQLinjectionLogin(user);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        if (userFromDB != null) {
            System.out.println(userFromDB.getUsername());
            System.out.println(userFromDB.getPassword());
            return true;
        } else {
            System.out.println("cannot found");
            return false;

        }
    }
    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }
}
