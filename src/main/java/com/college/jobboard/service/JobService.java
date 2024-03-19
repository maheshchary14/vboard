package com.college.jobboard.service;

import com.college.jobboard.exception.JobNotFoundException;
import com.college.jobboard.model.Job;
import com.college.jobboard.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return jobRepository.save(job);
    }

    public Job getJobById(Long jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            return optionalJob.get();
        }
        throw new JobNotFoundException("Job not found with id: " + jobId);
    }

    public List<Job> getAllJobs(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Job> jobPage = jobRepository.findAll(pageRequest);
        return new ArrayList<>(jobPage.getContent());
    }

}
