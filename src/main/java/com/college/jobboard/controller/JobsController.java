package com.college.jobboard.controller;

import com.college.jobboard.dto.JobPostDTO;
import com.college.jobboard.dto.JobResponseDTO;
import com.college.jobboard.facade.JobsFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobsController {

    @Autowired
    private JobsFacade jobsFacade;

    @PostMapping("/create")
    public ResponseEntity<JobResponseDTO> createJob(@Valid @RequestBody JobPostDTO jobDTO) {
        return jobsFacade.createJob(jobDTO);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponseDTO> getJob(@PathVariable Long jobId) {
        return jobsFacade.getJob(jobId);
    }

    @GetMapping("")
    public ResponseEntity<List<JobResponseDTO>> getAllJobsByStatus(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   @RequestParam(required = false) String status) {
        return jobsFacade.getAllJobsByStatus(page, size, status);
    }

    @PutMapping("/{jobId}")
    public JobResponseDTO updateJob(@PathVariable Long jobId, @RequestBody JobPostDTO jobDTO) {
        return jobsFacade.updateJob(jobId, jobDTO);
    }
}
