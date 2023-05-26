package com.project.service;
import com.project.entity.Feature;
import com.project.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {

    private FeatureRepository featureRepository;

    @Autowired
    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public Feature findById(Integer featureId) {
        return featureRepository.findById(featureId).orElse(null);
    }

    public List<Feature> findAll() {
        return featureRepository.findAll();
    }


    public Feature createFeature(Feature feature) {
        return featureRepository.save(feature);
    }

    public Feature updateFeature(Feature feature) {
        return featureRepository.save(feature);
    }

    public void deleteFeature(Integer featureId) {
        featureRepository.deleteById(featureId);
    }
}

