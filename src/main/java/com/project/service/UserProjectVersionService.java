package com.project.service;

import com.project.entity.ProjectVersion;
import com.project.entity.User;
import com.project.entity.UserDTO;
import com.project.entity.UserProjectVersion;
import com.project.entity.enums.UserRole;
import com.project.repository.UserProjectVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProjectVersionService {
    private final UserProjectVersionRepository userProjectVersionRepository;

    @Autowired
    public UserProjectVersionService(UserProjectVersionRepository userProjectVersionRepository) {
        this.userProjectVersionRepository = userProjectVersionRepository;
    }

    public List<UserProjectVersion> findAll() {
        return userProjectVersionRepository.findAll();
    }

    public UserProjectVersion add(UserProjectVersion userProjectVersion) {
        return userProjectVersionRepository.save(userProjectVersion);
    }

    public List<ProjectVersion> findProjectVersionsByUserId(Integer userId) {
        return userProjectVersionRepository.findProjectVersionsByUserId(userId);
    }

    public List<UserDTO> findUsersByProjectVersionId(Integer projectVersionId) {
        List<User> users = userProjectVersionRepository.findUsersByProjectVersionId(projectVersionId);
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
