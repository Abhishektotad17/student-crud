package com.example.demo.controller;

import com.example.demo.dto.PaginatedStudentRespDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.StudentSearchDto;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    private String createStudent(@RequestBody(required = true)StudentDto dto) {
        return studentService.createStudent(dto);
    }

    @PostMapping("/read-student")
    private StudentDto readStudent(@RequestBody StudentSearchDto dto) {
        return studentService.findStudent(dto);
    }

    @PostMapping("/all-students-paginated")
    private PaginatedStudentRespDto getPaginatedStudents(@RequestBody Map<String, Integer> pageDetails) {
        return studentService.getStudents(pageDetails.get("page"), pageDetails.get("size"));
    }

    @PostMapping("/filtered-students")
    private List<StudentDto> getFilteredStudent(@RequestBody Map<String, Integer> marks) {
        return studentService.getFilteredStudents(marks.get("min"), marks.get("max"));
    }
}
