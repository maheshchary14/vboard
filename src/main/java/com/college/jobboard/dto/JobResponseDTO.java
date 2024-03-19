package com.college.jobboard.dto;

import lombok.Data;

import java.util.Date;

@Data
public class JobResponseDTO {
    private String id;
    private String title;
    private String description;
    private String jobType;
    private String status;
    private Date expiryDate;
    private boolean allowBacklogs;
}