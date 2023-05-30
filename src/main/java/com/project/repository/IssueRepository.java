package com.project.repository;

import com.project.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {
    @Query("SELECT i FROM Issue i WHERE i.task.taskId = :taskId")
    List<Issue> findIssuesByTaskId(int taskId);
}
