package com.project.service;

import com.project.entity.Feature;
import com.project.entity.FeatureDTO;
import com.project.entity.ProjectVersion;
import com.project.entity.Task;
import com.project.repository.FeatureRepository;
import com.project.repository.ProjectVersionRepository;
import com.project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeatureService {
    private FeatureRepository featureRepository;
    private ProjectVersionRepository projectVersionRepository;
    private TaskRepository taskRepository;

    @Autowired
    public FeatureService(FeatureRepository featureRepository,
                          ProjectVersionRepository projectVersionRepository,
                          TaskRepository taskRepository) {
        this.featureRepository = featureRepository;
        this.projectVersionRepository = projectVersionRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void deleteFeature(Integer featureId) {
        Feature feature = featureRepository.findById(featureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid featureId: " + featureId));

        // Xóa tất cả các Task có cùng Feature Id
        List<Task> tasks = taskRepository.findTasksByFeatureId(featureId);
        for (Task task : tasks) {
            taskRepository.delete(task);
        }

        // Xóa Feature
        featureRepository.delete(feature);
    }

    public void updateFeature(Feature feature) {
        featureRepository.save(feature);
    }

    public void addFeature(Feature feature) {
        featureRepository.save(feature);
    }

    public List<Feature> getFeaturesByProjectVersion(Integer projectVersionId) {
        return featureRepository.findByProjectVersionId(projectVersionId);
    }

    public Feature getFeatureById(Integer featureId) {
        return featureRepository.findById(featureId).orElse(null);
    }

    public List<Task> getTasksByFeatureId(Integer featureId) {
        return taskRepository.findTasksByFeatureId(featureId);
    }

    public List<FeatureDTO> getFeatureDTOsByProjectVersionId(Integer projectVersionId) {
        List<Feature> features = featureRepository.findByProjectVersionId(projectVersionId);
        List<FeatureDTO> featureDTOs = new ArrayList<>();

        for (Feature feature : features) {
            List<Task> tasks = taskRepository.findTasksByFeatureId(feature.getId());
            FeatureDTO featureDTO = new FeatureDTO(feature, tasks);
            featureDTOs.add(featureDTO);
        }

        return featureDTOs;
    }
}

