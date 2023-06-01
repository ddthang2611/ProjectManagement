package com.project.service;

import com.project.entity.Issue;
import com.project.entity.Task;
import com.project.entity.TaskDTO;
import com.project.repository.IssueRepository;
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



    @Transactional
    public void deleteTask(Integer taskId) {

        taskRepository.deleteById(taskId);
    }

    public void updateTask(Task task) {
        taskRepository.save(task);
    }

    public void addTask(Task task) {
        taskRepository.save(task);
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
    }

}

