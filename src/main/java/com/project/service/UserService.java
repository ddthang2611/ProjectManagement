package com.project.service;

import com.project.entity.User;
import com.project.entity.UserDTO;
import com.project.entity.enums.UserRole;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if (userFromDB != null && userFromDB.isActive()) {
            System.out.println(userFromDB.getUsername());
            System.out.println(userFromDB.getPassword());
            return true;
        } else {
            System.out.println("cannot found");
            return false;

        }
    }
    public User getUserByUsername(String username){
        Optional<User> user = userRepository.getUserByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public User addUser(String username, String password, UserRole role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setActive(true);

        return userRepository.save(user);
    }




    public void deactivateUser(int userId){
        userRepository.deactivateUser(userId);
    }
    public User updateUserRole(int userId, UserRole newRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);

        return userRepository.save(user);
    }

    public User updatePassword(int userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(newPassword);

        return userRepository.save(user);
    }
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            if(user.getRole() != UserRole.ADMIN) {
                if (user.isActive()) {
                    UserDTO userDTO = new UserDTO(user.getUserId(), user.getUsername(), user.getRole());
                    userDTOs.add(userDTO);
                }
            }
        }

        return userDTOs;
    }



}
