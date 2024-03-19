package com.college.jobboard.facade;

import com.college.jobboard.dto.LoginRequestDTO;
import com.college.jobboard.dto.SignupRequestDTO;
import com.college.jobboard.dto.UserDTO;
import com.college.jobboard.exception.UserAlreadyExistException;
import com.college.jobboard.model.User;
import com.college.jobboard.model.enums.Role;
import com.college.jobboard.service.UserService;
import com.college.jobboard.utils.EntityDTOMapper;
import com.college.jobboard.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UsersFacade {
    @Autowired
    private EntityDTOMapper mapper;
    @Autowired
    private UserService userService;

    public ResponseEntity<?> authenticateUser(LoginRequestDTO loginRequestDTO) {
        User user = userService.getUserByUsername(loginRequestDTO.username());
        if (user == null) return new ResponseEntity<>("No user found with the given username", HttpStatus.UNAUTHORIZED);
        if (PasswordUtils.matchPassword(loginRequestDTO.password(), user.getPassword())) {
            return new ResponseEntity<>(mapper.convertToDTO(user, UserDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<UserDTO> createUserAccount(SignupRequestDTO signupRequestDTO) {
        if (userService.getUserByUsername(signupRequestDTO.getUsername()) != null) {
            throw new UserAlreadyExistException("User already exists with the given username!");
        }
        User user = new User();
        user.setUsername(signupRequestDTO.getUsername());
        user.setPassword(PasswordUtils.hashPassword(signupRequestDTO.getPassword()));
        user.setEmail(signupRequestDTO.getEmail());
        user.setRole(Role.PLACEMENT_STAFF);
        user.setCreatedAt(new Date());

        User savedUser = userService.save(user);
        return new ResponseEntity<>(mapper.convertToDTO(savedUser, UserDTO.class), HttpStatus.CREATED);
    }
}
