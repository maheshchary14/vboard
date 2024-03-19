package com.college.jobboard.utils;

import com.college.jobboard.model.enums.Role;
import com.college.jobboard.model.Student;
import com.college.jobboard.model.User;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static List<Student> excelToStudents(InputStream inputStream) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<User> users = new ArrayList<>();
            List<Student> students = new ArrayList<>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // Skip header row
                if (currentRow.getRowNum() == 0) {
                    continue;
                }

                Iterator<Cell> cells = currentRow.iterator();

                // Create a new student-user
                User user = new User();
                Student student = new Student();

                while (cells.hasNext()) {
                    Cell currentCell = cells.next();
                    int columnIndex = currentCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0: user.setUsername(currentCell.getStringCellValue());break;
                        case 1: user.setEmail(currentCell.getStringCellValue());break;
                        case 2: user.setPassword(PasswordUtils.hashPassword(currentCell.getStringCellValue()));break;
                        case 3: student.setFullName(currentCell.getStringCellValue());break;
                        case 4: student.setStream(currentCell.getStringCellValue());break;
                        case 5: student.setCgpa(currentCell.getNumericCellValue());break;
                        case 6: student.setGraduationYear(Year.of((int) currentCell.getNumericCellValue()));break;
                        default: break;
                    }
                }
                Date currentDate = new Date();
                user.setRole(Role.STUDENT);
                user.setCreatedAt(currentDate);
                student.setUser(user);
                student.setCreatedAt(currentDate);

                students.add(student);
            }
            return students;
        }
    }
}

