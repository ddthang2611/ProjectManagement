package com.project.service;

import com.project.entity.EmployeeAnalysis;
import com.project.entity.User;
import com.project.repository.TaskRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service

public class EmployeeAnalysisService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;
    public EmployeeAnalysis getEmployeeAnalysis(int employeeId) {
        User user = userRepository.findById(employeeId).orElse(null);
        String employeeName = user.getUsername();

        int assignedTasks = taskRepository.countByAssignedTo(employeeId);
        int completedTasks = taskRepository.countCompletedTasks(employeeId);
        LocalDate currentDate = LocalDate.now();
        Date convertedDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Double averageDaysOverdue = taskRepository.calculateAverageDaysOverdue(employeeId, convertedDate);
        double avgDaysOverdue = averageDaysOverdue != null ? averageDaysOverdue.doubleValue() : 0.0;
        int overdueTasks = taskRepository.countOverdueTasks(employeeId, convertedDate);
        return new EmployeeAnalysis(assignedTasks, completedTasks, overdueTasks, avgDaysOverdue,employeeName);
    }
    public int countTasksByEmployeeId(int employeeId){
        return taskRepository.countByAssignedTo(employeeId);
    }

    public int getTotalTask(){
        return taskRepository.getTotalTask();
    }


}
