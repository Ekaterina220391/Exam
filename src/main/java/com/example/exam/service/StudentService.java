package com.example.exam.service;

import com.example.exam.model.Faculty;
import com.example.exam.model.Student;
import com.example.exam.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {


    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        // Критерий 2: сообщения начинаются с "Was invoked method for..."
        logger.info("Was invoked method for add student");
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for find student");
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            // Критерий 3: логирование ошибки
            logger.error("Was invoked method for find student, but student with id {} not found", id);
        }
        return student;
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find students by age between");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Student updateStudent(Long id, Student student) {
        logger.info("Was invoked method for update student");
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent == null) {
            logger.error("Was invoked method for update student, but student with id {} not found", id);
            return null;
        }
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());

        return studentRepository.save(existingStudent);
    }

    public Faculty getStudentFaculty(Long id) {
        logger.info("Was invoked method for get student faculty");
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            logger.error("Was invoked method for get student faculty, but student with id {} not found", id);
            return null;
        }
        return student.getFaculty();
    }

    public long countAllStudents() {
        logger.info("Was invoked method for count all students");
        return studentRepository.countAllStudents();
    }

    public double averageAge() {
        logger.info("Was invoked method for average age");
        return studentRepository.averageAge();
    }

    public List<Student> getLast5Students() {
        logger.info("Was invoked method for get last 5 students");
        return studentRepository.findLast5Students();
    }
    public List<String> getAllStudentsStartingWithA() {
        logger.info("Was invoked method for get all students starting with A");

        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }
    public double getAverageAgeByStream() {
        logger.info("Was invoked method for get average age by stream");

        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }
}

