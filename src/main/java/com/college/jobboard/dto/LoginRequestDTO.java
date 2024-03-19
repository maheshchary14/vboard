package com.college.jobboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record LoginRequestDTO(
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 20 characters")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, max = 40, message = "Password must be between 8 and 40 characters")
        String password) {
}
