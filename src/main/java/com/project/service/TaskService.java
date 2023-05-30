package com.project.service;

import com.project.entity.Issue;
import com.project.entity.Task;
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



}

