package com.example.demo.service;

import com.example.demo.dto.PaginatedStudentRespDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.StudentSearchDto;

import java.util.List;

public interface StudentService {
    String createStudent(StudentDto dto);

    StudentDto findStudent(StudentSearchDto dto);

    PaginatedStudentRespDto getStudents(Integer start, Integer end);

    List<StudentDto> getFilteredStudents(Integer min, Integer max);
}
