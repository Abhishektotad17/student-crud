package com.example.demo.service.impl;

import com.example.demo.dto.PaginatedStudentRespDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.StudentSearchDto;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Override
    public String createStudent(StudentDto dto) {
        Optional<Student> student = studentRepository.findByName(dto.getName());
        if (student.isPresent()) {
            return "Student already exists please create with different name";
        }
        Student model = new Student();
        model.setName(dto.getName().toLowerCase());
        model.setTotalMark(dto.getMark());
        model = studentRepository.save(model);
        return "student created with id : " + model.getId().toString();
    }

    @Override
    public StudentDto findStudent(StudentSearchDto dto) {
        if (dto.getId() != null) {
            Optional<Student> model = studentRepository.findById(dto.getId());
            if (model.isEmpty()) {
                throw new ValidationException("No Student found with id " + dto.getId().toString());
            }
            StudentDto resp = new StudentDto();
            resp.setId(model.get().getId());
            resp.setMark(model.get().getTotalMark());
            resp.setName(model.get().getName());
            return resp;
        }

        if (dto.getName() != null && dto.getName().length() > 0) {
            List<Student> result = studentRepository.searchByName(dto.getName().toLowerCase());
            if (result.size() == 0) {
                throw new ValidationException("No student found with this name " + dto.getName());
            }
            Student model = result.get(0);
            StudentDto resp = new StudentDto();
            resp.setId(model.getId());
            resp.setMark(model.getTotalMark());
            resp.setName(model.getName());
            return resp;
        }
        throw new ValidationException("Something went wrong");
    }

    @Override
    public PaginatedStudentRespDto getStudents(Integer page, Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
        Pageable pageable = PageRequest.of(page, size);
        List<Student> students = studentRepository.findAll(pageable).getContent();

        if (students.size() == 0) {
            throw new ValidationException("No students data found");
        }

        PaginatedStudentRespDto resp = new PaginatedStudentRespDto();
        List<StudentDto> studentDtoList = new ArrayList<>();
        students.stream().forEach(student-> {
            StudentDto stu = new StudentDto();
            stu.setId(student.getId());
            stu.setName(student.getName());
            stu.setMark(student.getTotalMark());
            studentDtoList.add(stu);
        });
        resp.setStart(size);
        resp.setEnd(size+10);
        resp.setStudentDtoList(studentDtoList);
        return resp;
    }

    @Override
    public List<StudentDto> getFilteredStudents(Integer min, Integer max) {
        List<Student> studentList = new ArrayList<>();
        if (min != null && max!= null) {
            studentList = studentRepository.findByTotalMarkBetween(min, max);
        }
        else if (min != null) {
            studentList = studentRepository.findByTotalMarkGreaterThanEqual(min);
        }
        else if (max != null) {
            studentList = studentRepository.findByTotalMarkLessThanEqual(max);
        } else {
            throw new ValidationException("Something went wrong");
        }

        List<StudentDto> resp = new ArrayList<>();
        studentList.stream().forEach(s->{
            StudentDto studentDto = new StudentDto();
            studentDto.setId(s.getId());
            studentDto.setMark(s.getTotalMark());
            studentDto.setName(s.getName());
            resp.add(studentDto);
        });
        return resp;
    }
}
