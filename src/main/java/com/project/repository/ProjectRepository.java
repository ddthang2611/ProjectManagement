package com.project.repository;

import com.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Modifying
    @Query("UPDATE Project p SET p.enable = :enable WHERE p.projectId = :projectId")
    int setEnableById(@Param("projectId") Integer projectId, @Param("enable") boolean enable);





}

