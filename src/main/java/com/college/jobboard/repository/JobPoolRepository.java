package com.college.jobboard.repository;

import com.college.jobboard.model.JobPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobPoolRepository extends JpaRepository<JobPool, Long> {
    Optional<JobPool> findByJob_IdAndUser_Username(Long jobId, String username);
    boolean existsByJob_IdAndUser_Username(Long jobId, String username);
}
