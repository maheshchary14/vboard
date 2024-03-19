package com.college.jobboard.controller;

import com.college.jobboard.facade.JobPoolFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-pool")
public class JobPoolController {
    @Autowired
    private JobPoolFacade jobPoolFacade;

    @PostMapping("/view/{jobId}/{username}")
    public ResponseEntity<String> viewJob(@PathVariable Long jobId,
                                          @PathVariable String username) {
        jobPoolFacade.viewJob(jobId, username);
        return ResponseEntity.ok("Job viewed successfully");
    }

    @PostMapping("/apply/{jobId}/{username}")
    public ResponseEntity<String> applyToJob(@PathVariable Long jobId,
                                             @PathVariable String username) {
        boolean applied = jobPoolFacade.applyToJob(jobId, username);
        if (applied) return ResponseEntity.ok("Applied to job successfully");
        return ResponseEntity.status(403).body("Students having backlogs cannot apply to this job");
    }
}
