package com.project.service;

import com.project.entity.*;
import com.project.repository.FeatureRepository;
import com.project.repository.IssueRepository;
import com.project.repository.ProjectVersionRepository;
import com.project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private FeatureRepository featureRepository;
    @Autowired
    private ProjectVersionRepository projectVersionRepository;



    @Transactional
    public void deleteTask(Integer taskId) {

        taskRepository.deleteById(taskId);
    }

    public void updateTask(Task task) {
        taskRepository.save(task);
    }

    public void addTask(Task task) {

        taskRepository.save(task);
        updateProgress(task);
    }


    public Task getTaskById(Integer taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public List<Issue> getIssuesByTaskId(Integer taskId) {
        return issueRepository.findIssuesByTaskId(taskId);
    }

    public TaskDTO getTaskDTOById(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            return new TaskDTO(
                    task.getTaskId(),
                    task.getTaskName(),
                    task.getTaskDescription(),
                    task.getStartDate(),
                    task.getEstimatedEndDate(),
                    task.getEndDate(),
                    task.getProgress(),
                    task.getStatus(),
                    task.getPriority()
            );
        }
        return null;
    }
    public void updateTask(TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskDTO.getTaskId()).orElse(null);
        if (task != null) {
            task.setTaskName(taskDTO.getTaskName());
            task.setTaskDescription(taskDTO.getTaskDescription());
            task.setStartDate(taskDTO.getStartDate());
            task.setEstimatedEndDate(taskDTO.getEstimatedEndDate());
            task.setEndDate(taskDTO.getEndDate());
            task.setProgress(taskDTO.getProgress());
            task.setStatus(taskDTO.getStatus());
            task.setPriority(taskDTO.getPriority());
            taskRepository.save(task);
        }
        updateProgress(task);
    }

    public void updateProgress(Task task) {
//        Task savedTask = taskRepository.save(task);

        // Tính toán progress mới cho Feature
        Feature feature = task.getFeature();
        List<Task> featureTasks = featureRepository.findTasksByFeatureId(feature.getId());
        int totalProgress = 0;
        for (Task featureTask : featureTasks) {
            totalProgress += featureTask.getProgress();
        }
        int averageProgress = featureTasks.isEmpty() ? 0 : totalProgress / featureTasks.size();
        feature.setProgress(averageProgress);
        featureRepository.save(feature);

        // Tính toán progress mới cho ProjectVersion
        ProjectVersion projectVersion = feature.getProjectVersion();
        List<Feature> projectVersionFeatures = featureRepository.findByProjectVersionId(projectVersion.getProjectVersionId());
        totalProgress = 0;
        for (Feature projectVersionFeature : projectVersionFeatures) {
            totalProgress += projectVersionFeature.getProgress();
        }
        averageProgress = projectVersionFeatures.isEmpty() ? 0 : totalProgress / projectVersionFeatures.size();
        projectVersion.setProgress(averageProgress);
        projectVersionRepository.save(projectVersion);

    }

}

