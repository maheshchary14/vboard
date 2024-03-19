package com.college.jobboard.controller;

import com.college.jobboard.dto.StudentCreationRequestDTO;
import com.college.jobboard.dto.StudentResponseDTO;
import com.college.jobboard.facade.StudentsFacade;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/students")
public class StudentsController {
    @Autowired
    private StudentsFacade studentsFacade;

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<String> createStudentAccount(@Valid @RequestBody StudentCreationRequestDTO requestDTO) {
        return studentsFacade.createStudentAccount(requestDTO);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        return studentsFacade.uploadExcelFile(file);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<StudentResponseDTO> getStudentByUsername(@PathVariable String username) {
        StudentResponseDTO studentDTO = studentsFacade.getStudentByUsername(username);
        if (studentDTO != null) {
            return ResponseEntity.ok(studentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
