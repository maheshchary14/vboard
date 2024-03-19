package com.college.jobboard.service;

import com.college.jobboard.exception.JobNotFoundException;
import com.college.jobboard.model.Job;
import com.college.jobboard.repository.JobRepository;
import com.college.jobboard.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public Job save(Job job) {
        job.setCreatedAt(new Date());
        job.setStatus(Constants.JOB_STATUS_ACTIVE);
        return jobRepository.save(job);
    }

    public Job getJobById(Long jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            return optionalJob.get();
        }
        throw new JobNotFoundException("Job not found with id: " + jobId);
    }

    public List<Job> getAllJobsByStatus(int page, int size, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Job> jobPage;
        if (status != null && !status.isEmpty()) {
            jobPage = jobRepository.findByStatus(status, pageable);
        } else {
            jobPage = jobRepository.findAll(pageable);
        }
        return jobPage.getContent();
    }

}
