package com.college.jobboard.dto;

import lombok.Data;

import java.time.Year;

@Data
public class StudentResponseDTO {
    private String username;
    private String email;
    private String fullName;
    private Double cgpa;
    private String stream;
    private Year graduationYear;
}
