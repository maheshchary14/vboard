package com.college.jobboard.service;

import com.college.jobboard.model.JobPool;
import com.college.jobboard.repository.JobPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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
        if (jobPoolRepository.existsByJob_IdAndUser_Username(jobId, username))
            throw new RuntimeException("User has already view the job");
        JobPool jobPool = new JobPool();
        jobPool.setJob(jobService.getJobById(jobId));
        jobPool.setUser(userService.getUserByUsername(username));
        jobPool.setViewedAt(new Date());
        jobPoolRepository.save(jobPool);
    }

    public void recordJobApplication(Long jobId, String username) {
        Optional<JobPool> optionalJobPool = jobPoolRepository.findByJob_IdAndUser_Username(jobId, username);
        JobPool jobPool;
        if (optionalJobPool.isPresent()) {
            if (optionalJobPool.get().getAppliedAt()!=null) {
                throw new RuntimeException("User has already applied to the job");
            }
            else {
                jobPool = optionalJobPool.get();
            }
        }
        else {
            jobPool = new JobPool();

            jobPool.setJob(jobService.getJobById(jobId));
            jobPool.setUser(userService.getUserByUsername(username));
            jobPool.setViewedAt(new Date());
        }
        jobPool.setAppliedAt(new Date());
        jobPoolRepository.save(jobPool);
        emailSenderService.sendMail(
                jobPool.getUser().getEmail(),
                "Job Application Succes",
                "You have successfully applied to the job - " + jobPool.getJob().getTitle()
        );
    }
}
