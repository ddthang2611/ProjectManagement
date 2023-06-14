package com.project.repository;
import com.project.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    // Tìm tất cả các tác vụ thuộc về một tính năng (feature) dựa trên featureId
    @Query("SELECT t FROM Task t WHERE t.feature.id = :featureId")
    List<Task> findTasksByFeatureId(int featureId);


}

