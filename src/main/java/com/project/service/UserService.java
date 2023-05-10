package com.project.service;

import com.project.entity.User;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public boolean checkLogin(User user) {
        User userFromDB = null;
        try {
            userFromDB = userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
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
