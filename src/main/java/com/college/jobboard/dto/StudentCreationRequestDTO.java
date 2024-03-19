package com.college.jobboard.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Year;

@Data
public class StudentCreationRequestDTO {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "CGPA is required")
    private Double cgpa;

    @NotBlank(message = "Stream is required")
    @Pattern(regexp = "^(CSE|CSM|IT|ECE|EEE|CIVIL|MECH)$", message = "Invalid stream")
    private String stream;

    @NotNull(message = "Graduation year is required")
    private Year graduationYear;

}
