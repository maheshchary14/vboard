package com.college.jobboard.dto;

import lombok.Data;

@Data
public class JobResponseDTO {
    private String id;
    private String title;
    private String description;
    private String jobType;
}