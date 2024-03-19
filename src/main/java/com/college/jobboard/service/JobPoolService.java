package com.college.jobboard.service;

import com.college.jobboard.model.JobPool;
import com.college.jobboard.repository.JobPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JobPoolService {
    @Autowired
    private JobPoolRepository jobPoolRepository;
    @Autowired
    private JobService jobService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailSenderService;

    public void recordJobView(Long jobId, String username) {
        JobPool jobPool = new JobPool();
        jobPool.setJob(jobService.getJobById(jobId));
        jobPool.setUser(userService.getUserByUsername(username));
        jobPool.setViewedAt(new Date());
        jobPoolRepository.save(jobPool);
    }

    public void recordJobApplication(Long jobId, String username) {
        JobPool jobPool = new JobPool();
        jobPool.setJob(jobService.getJobById(jobId));
        jobPool.setUser(userService.getUserByUsername(username));
        jobPool.setViewedAt(new Date());
        jobPoolRepository.save(jobPool);

        emailSenderService.sendMail(
                jobPool.getUser().getEmail(),
                "Job Application Succes",
                "You have successfully applied to the job - " + jobPool.getJob().getTitle()
        );
    }
}
