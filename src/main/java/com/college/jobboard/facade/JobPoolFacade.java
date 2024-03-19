package com.college.jobboard.facade;

import com.college.jobboard.service.EmailSenderService;
import com.college.jobboard.service.JobPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobPoolFacade {
    @Autowired
    private JobPoolService jobPoolService;

    public void viewJob(Long jobId, String username) {
        jobPoolService.recordJobView(jobId, username);
    }

    public void applyToJob(Long jobId, String username) {
        jobPoolService.recordJobApplication(jobId, username);
    }
}
