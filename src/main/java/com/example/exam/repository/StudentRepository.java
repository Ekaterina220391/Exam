package com.example.exam.repository;

import com.example.exam.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int min, int max);
    @Query("SELECT COUNT(s) FROM Student s")
    long countAllStudents();


    @Query("SELECT AVG(s.age) FROM Student s")
    double averageAge();


    @Query("SELECT s FROM Student s ORDER BY s.id DESC LIMIT 5")
    List<Student> findLast5Students();
}
