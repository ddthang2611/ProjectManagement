package com.project.repository;

import com.project.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
    @Query("SELECT f FROM Feature f WHERE f.projectVersion.projectVersionId = :projectVersionId")
    List<Feature> findByProjectVersionId(@Param("projectVersionId") Integer projectVersionId);


}
