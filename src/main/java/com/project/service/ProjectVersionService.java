package com.project.service;

import com.project.entity.ProjectVersion;
import com.project.repository.ProjectVersionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectVersionService {
    private final ProjectVersionRepository projectVersionRepository;

    public ProjectVersionService(ProjectVersionRepository projectVersionRepository) {
        this.projectVersionRepository = projectVersionRepository;
    }

    public void disableProjectVersion(Integer projectVersionId) {
        ProjectVersion projectVersion = projectVersionRepository.findById(projectVersionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid projectVersionId: " + projectVersionId));

        projectVersion.setEnable(false);
        projectVersionRepository.save(projectVersion);
    }

    public List<ProjectVersion> getProjectVersionsByProjectId(Integer projectId) {
        return projectVersionRepository.getProjectVersionByProjectId(projectId);
    }
}
