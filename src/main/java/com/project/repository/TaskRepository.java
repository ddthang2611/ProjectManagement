package com.project.repository;
import com.project.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Override
    @Query("SELECT t FROM Task t WHERE t.enable = true")
    List<Task> findAll();

    @Override
    @Query("SELECT t FROM Task t WHERE t.enable = true AND t.taskId = :id")
    Optional<Task> findById(@Param("id") Integer id);

    @Query("SELECT t FROM Task t WHERE t.feature.id = :featureId AND t.enable = true")
    List<Task> findTasksByFeatureId(@Param("featureId") int featureId);

    @Modifying
    @Query("UPDATE Task t SET t.enable = :enable WHERE t.taskId = :taskId")
    int setEnableById(@Param("taskId") Integer taskId, @Param("enable") boolean enable);

}

