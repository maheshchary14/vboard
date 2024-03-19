package com.college.jobboard.repository;

import com.college.jobboard.model.JobPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPoolRepository extends JpaRepository<JobPool, Long> {
    // Add custom query methods if needed
}
