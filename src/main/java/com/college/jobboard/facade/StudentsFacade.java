package com.college.jobboard.facade;

import com.college.jobboard.dto.StudentCreationRequestDTO;
import com.college.jobboard.dto.StudentResponseDTO;
import com.college.jobboard.exception.UserAlreadyExistException;
import com.college.jobboard.model.Student;
import com.college.jobboard.model.User;
import com.college.jobboard.model.enums.Role;
import com.college.jobboard.service.StudentService;
import com.college.jobboard.service.UserService;
import com.college.jobboard.utils.EntityDTOMapper;
import com.college.jobboard.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Component
public class StudentsFacade {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @Autowired
    private EntityDTOMapper mapper;

    public ResponseEntity<String> createStudentAccount(StudentCreationRequestDTO requestDTO) {
        if (userService.getUserByUsername(requestDTO.getUsername()) != null) {
            throw new UserAlreadyExistException("User already exists with given username");
        }
        Date currentDate = new Date();
        // Create a new user
        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(PasswordUtils.hashPassword(requestDTO.getPassword()));
        user.setEmail(requestDTO.getEmail());
        user.setRole(Role.STUDENT);
        user.setCreatedAt(currentDate);

        // Create a new student
        Student student = new Student();
        student.setUser(user);
        student.setFullName(requestDTO.getFullName());
        student.setCgpa(requestDTO.getCgpa());
        student.setStream(requestDTO.getStream());
        student.setGraduationYear(requestDTO.getGraduationYear());
        student.setCreatedAt(currentDate);

        // Save the student
        studentService.save(student);

        return ResponseEntity.status(HttpStatus.CREATED).body("Student account created successfully");
    }

    public ResponseEntity<String> uploadExcelFile(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }
        try {
            studentService.saveStudentsFromExcel(file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Data uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    public StudentResponseDTO getStudentByUsername(String username) {
        Student student = studentService.getStudentByUsername(username);
        if (student != null) {
            StudentResponseDTO studentResponseDTO = mapper.convertToDTO(student, StudentResponseDTO.class);
            studentResponseDTO.setUsername(student.getUser().getUsername());
            studentResponseDTO.setEmail(student.getUser().getEmail());
            return studentResponseDTO;
        }
        return null;
    }
}
