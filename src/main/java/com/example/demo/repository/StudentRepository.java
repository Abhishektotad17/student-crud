package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    List<Student> findByTotalMarkBetween(Integer minMarks, Integer maxMarks);

    List<Student> findByTotalMarkGreaterThanEqual(Integer minMarks);

    List<Student> findByTotalMarkLessThanEqual(Integer maxMarks);
    Optional<Student> findByName(String name);

    @Query("SELECT s FROM Student s WHERE s.name LIKE %?1%")
    List<Student> searchByName(String name);
}
