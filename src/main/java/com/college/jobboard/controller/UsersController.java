package com.college.jobboard.controller;

import com.college.jobboard.dto.LoginRequestDTO;
import com.college.jobboard.dto.SignupRequestDTO;
import com.college.jobboard.dto.UserDTO;
import com.college.jobboard.facade.UsersFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersFacade usersFacade;

    @GetMapping("/auth")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return usersFacade.authenticateUser(loginRequestDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUserAccount(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        return usersFacade.createUserAccount(signupRequestDTO);
    }
}
