package com.college.jobboard.repository;

import com.college.jobboard.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // Add custom query methods if needed
}
