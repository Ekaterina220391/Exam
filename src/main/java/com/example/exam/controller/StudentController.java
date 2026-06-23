package com.example.exam.controller;

import com.example.exam.model.Faculty;
import com.example.exam.model.Student;
import com.example.exam.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/by-age")
    public List<Student> getStudentsByAgeBetween(@RequestParam int min,
                                                 @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAllStudents() {
        long count = studentService.countAllStudents();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> averageAge() {
        double avgAge = studentService.averageAge();
        return ResponseEntity.ok(avgAge);
    }

    @GetMapping("/last-5")
    public ResponseEntity<List<Student>> getLast5Students() {
        List<Student> students = studentService.getLast5Students();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/names-starting-with-a")
    public List<String> getNamesStartingWithA() {
        return studentService.getAllStudentsStartingWithA();
    }

    @GetMapping("/average-age-stream")
    public double getAverageAgeByStream() {
        return studentService.getAverageAgeByStream();
    }

    @GetMapping("/print-parallel")
    public void printParallel() {
        List<Student> students = studentService.getAllStudents();

        if (students.size() < 6) {
            System.out.println("Нужно минимум 6 студентов в базе!");
            return;
        }


        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());


        new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        }).start();


        new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        }).start();
    }


    @GetMapping("/print-synchronized")
    public void printSynchronized() {
        List<Student> students = studentService.getAllStudents();

        if (students.size() < 6) {
            System.out.println("Нужно минимум 6 студентов в базе!");
            return;
        }


        printNameSync(students.get(0).getName());
        printNameSync(students.get(1).getName());

        new Thread(() -> {
            printNameSync(students.get(2).getName());
            printNameSync(students.get(3).getName());
        }).start();

        new Thread(() -> {
            printNameSync(students.get(4).getName());
            printNameSync(students.get(5).getName());
        }).start();
    }


    private synchronized void printNameSync(String name) {
        System.out.println(name);
    }
}