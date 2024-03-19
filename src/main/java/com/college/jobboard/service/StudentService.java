package com.college.jobboard.service;

import com.college.jobboard.dto.StudentResponseDTO;
import com.college.jobboard.model.Student;
import com.college.jobboard.repository.StudentRepository;
import com.college.jobboard.utils.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public void save(Student student) {
        studentRepository.save(student);
    }

    public void saveStudentsFromExcel(MultipartFile file) {
        try {
            List<Student> students = ExcelHelper.excelToStudents(file.getInputStream());
            studentRepository.saveAll(students);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store students data from Excel file: " + e.getMessage());
        }
    }

    public Student getStudentByUsername(String username) {
        Optional<Student> student = studentRepository.findByUser_Username(username);
        return student.get();
    }
}
