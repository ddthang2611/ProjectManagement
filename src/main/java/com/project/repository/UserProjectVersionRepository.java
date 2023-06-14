package com.project.repository;

import com.project.entity.UserProjectVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectVersionRepository extends JpaRepository<UserProjectVersion, Integer> {
}

