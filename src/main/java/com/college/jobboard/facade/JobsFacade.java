package com.college.jobboard.facade;

import com.college.jobboard.dto.JobPostDTO;
import com.college.jobboard.dto.JobResponseDTO;
import com.college.jobboard.model.Job;
import com.college.jobboard.model.enums.JobType;
import com.college.jobboard.service.JobService;
import com.college.jobboard.utils.EntityDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JobsFacade {
    @Autowired
    private JobService jobService;
    @Autowired
    private EntityDTOMapper mapper;

    public ResponseEntity<JobResponseDTO> createJob(JobPostDTO jobDTO) {
        Job savedJob = jobService.save(mapper.convertToEntity(jobDTO, Job.class));
        return new ResponseEntity<>(mapper.convertToDTO(savedJob, JobResponseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<JobResponseDTO> getJob(Long jobId) {
        return new ResponseEntity<>(mapper.convertToDTO(jobService.getJobById(jobId), JobResponseDTO.class), HttpStatus.OK);
    }

    public ResponseEntity<List<JobResponseDTO>> getAllJobs(int page, int size) {
        List<Job> jobList = jobService.getAllJobs(page, size);
        return new ResponseEntity<>(jobList.stream().map(this::convertToJobResponseDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    private JobResponseDTO convertToJobResponseDTO(Job job) {
        return mapper.convertToDTO(job, JobResponseDTO.class);
    }

    public JobResponseDTO updateJob(Long jobId, JobPostDTO jobDTO) {
        Job job = jobService.getJobById(jobId);

        // Update non-null job fields from jobDTO
        if (jobDTO.getTitle() != null) {
            job.setTitle(jobDTO.getTitle());
        }
        if (jobDTO.getDescription() != null) {
            job.setDescription(jobDTO.getDescription());
        }
        if (jobDTO.getJobType() != null) {
            job.setJobType(parseJobType(jobDTO.getJobType()));
        }
        Job savedJob = jobService.save(job);
        return mapper.convertToDTO(savedJob, JobResponseDTO.class);
    }

    private JobType parseJobType(String jobType) {
        return Optional.ofNullable(jobType)
                .map(String::toUpperCase)
                .map(JobType::valueOf)
                .orElse(null);
    }
}
