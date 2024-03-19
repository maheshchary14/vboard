package com.college.jobboard.dto;

import com.college.jobboard.model.enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private Role role;
    private boolean active = true;
}
