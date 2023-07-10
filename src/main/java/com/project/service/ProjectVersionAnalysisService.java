package com.project.service;


import com.project.entity.ProjectVersionAnalysis;
import com.project.repository.ProjectVersionAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
@Service
public class ProjectVersionAnalysisService {
    @Autowired
    ProjectVersionAnalysisRepository projectVersionAnalysisRepository;


    public ProjectVersionAnalysis getProjectVersionAnalysis(int projectVersionId) {
        int totalTasks = projectVersionAnalysisRepository.countTasksByProjectVersion(projectVersionId);
        int processingTasks = projectVersionAnalysisRepository.countProcessingTasks(projectVersionId);
        int completedTasks = projectVersionAnalysisRepository.countCompletedTasks(projectVersionId);
        int pendingTasks = projectVersionAnalysisRepository.countPendingTasks(projectVersionId);
        int postponedTasks = projectVersionAnalysisRepository.countPostponedTasks(projectVersionId);
        LocalDate currentDate = LocalDate.now();
        Date convertedDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Double averageDaysOverdue = projectVersionAnalysisRepository.calculateAverageDaysOverdue(projectVersionId, convertedDate);
        double avgDaysOverdue = averageDaysOverdue != null ? averageDaysOverdue.doubleValue() : 0.0;
        int overdueTasks = projectVersionAnalysisRepository.countOverdueTasks(projectVersionId, convertedDate);

        return new ProjectVersionAnalysis(projectVersionId, totalTasks, processingTasks, completedTasks, pendingTasks, postponedTasks, overdueTasks, avgDaysOverdue);
    }

}
