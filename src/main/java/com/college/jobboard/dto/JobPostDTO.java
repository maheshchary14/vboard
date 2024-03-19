package com.college.jobboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class JobPostDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Job type is required")
    @Pattern(regexp = "FULL_TIME|INTERNSHIP", message = "Invalid job type")
    private String jobType;
}
