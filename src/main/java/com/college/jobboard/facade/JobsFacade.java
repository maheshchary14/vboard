package com.college.jobboard.facade;

import com.college.jobboard.dto.JobPostDTO;
import com.college.jobboard.dto.JobResponseDTO;
import com.college.jobboard.model.Job;
import com.college.jobboard.model.JobPool;
import com.college.jobboard.model.enums.JobType;
import com.college.jobboard.service.JobService;
import com.college.jobboard.utils.EntityDTOMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JobsFacade {
    @Autowired
    private JobService jobService;
    @Autowired
    private EntityDTOMapper mapper;
    @Autowired
    private EntityManager entityManager;

    public ResponseEntity<JobResponseDTO> createJob(JobPostDTO jobDTO) {
        Job savedJob = jobService.save(mapper.convertToEntity(jobDTO, Job.class));
        return new ResponseEntity<>(mapper.convertToDTO(savedJob, JobResponseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<JobResponseDTO> getJob(Long jobId) {
        return new ResponseEntity<>(mapper.convertToDTO(jobService.getJobById(jobId), JobResponseDTO.class), HttpStatus.OK);
    }

    public ResponseEntity<List<JobResponseDTO>> getAllJobsByStatus(int page, int size, String status) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Job> criteriaQuery = criteriaBuilder.createQuery(Job.class);
//        Root<Job> root = criteriaQuery.from(Job.class);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        // Add predicate based on status
//        if (status != null && !status.isEmpty()) {
//            predicates.add(criteriaBuilder.equal(root.get("status"), status));
//        }
//
//        // Join with JobPool if username is provided
//        if (username != null && !username.isEmpty()) {
//            Join<Job, JobPool> jobPoolJoin = root.join("jobPool");
//            predicates.add(criteriaBuilder.equal(jobPoolJoin.get("user").get("username"), username));
//        }
//
//        criteriaQuery.where(predicates.toArray(new Predicate[0]));
//
//        // Perform pagination
//        TypedQuery<Job> typedQuery = entityManager.createQuery(criteriaQuery);
//        typedQuery.setFirstResult(page * size);
//        typedQuery.setMaxResults(size);
//
//        List<Job> jobs = typedQuery.getResultList();
//
        // Convert Job entities to JobResponseDTO objects
        List<JobResponseDTO> jobResponseDTOs = jobService.getAllJobsByStatus(page, size, status).stream()
                .map(this::convertToJobResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(jobResponseDTOs);

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
        if (jobDTO.getExpiryDate() != null) {
            job.setExpiryDate(jobDTO.getExpiryDate());
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

    public ResponseEntity<List<JobResponseDTO>> getEligibleJobsForStudent(int page, int size, String username, String status) {
        // Convert Job entities to JobResponseDTO objects
        List<JobResponseDTO> jobResponseDTOs = jobService.getAllJobsByStatus(page, size, status).stream()
                .map(this::convertToJobResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(jobResponseDTOs);
    }
}
