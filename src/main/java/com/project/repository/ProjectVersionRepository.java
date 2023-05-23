package com.project.repository;

import com.project.entity.ProjectVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectVersionRepository extends JpaRepository<ProjectVersion, Integer> {

    @Modifying
    @Query("UPDATE ProjectVersion pv SET pv.enable = :enable WHERE pv.projectVersionId = :projectVersionId")
    void setEnableById(@Param("projectVersionId") Integer projectVersionId, @Param("enable") boolean enable);

    @Query("SELECT pv FROM ProjectVersion pv WHERE pv.project.projectId = :projectId")
    List<ProjectVersion> getProjectVersionByProjectId(@Param("projectId") Integer projectId);
}
