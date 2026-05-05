package com.example.exam.controller;
import com.example.exam.model.Faculty;
import com.example.exam.model.Student;
import com.example.exam.repository.FacultyRepository;
import com.example.exam.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class StudentControllerTestResTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    void addStudent() {
        String body = """
                {"name":"Anna","age":20}
                """;

        ResponseEntity<Student> response = restTemplate.exchange(
                url("/student"),
                HttpMethod.POST,
                new HttpEntity<>(body, jsonHeaders()),
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Anna", response.getBody().getName());
        assertEquals(20, response.getBody().getAge());
    }

    @Test
    void getAllStudents() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url("/student"),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getStudent() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url("/student/1"),
                String.class
        );

        assertTrue(
                response.getStatusCode() == HttpStatus.OK ||
                        response.getStatusCode() == HttpStatus.NOT_FOUND
        );
    }

    @Test
    void updateStudent() {
        String body = """
                {"name":"Kate","age":21}
                """;

        ResponseEntity<String> response = restTemplate.exchange(
                url("/student/1"),
                HttpMethod.PUT,
                new HttpEntity<>(body, jsonHeaders()),
                String.class
        );

        assertTrue(
                response.getStatusCode() == HttpStatus.OK ||
                        response.getStatusCode() == HttpStatus.NOT_FOUND
        );
    }

    @Test
    void deleteStudent() {
        ResponseEntity<String> response = restTemplate.exchange(
                url("/student/1"),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                String.class
        );

        assertTrue(
                response.getStatusCode() == HttpStatus.OK ||
                        response.getStatusCode() == HttpStatus.NOT_FOUND
        );
    }

    @Test
    void getStudentsByAgeBetween() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url("/student/by-age?min=18&max=25"),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getStudentFaculty() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url("/student/1/faculty"),
                String.class
        );

        assertTrue(
                response.getStatusCode() == HttpStatus.OK ||
                        response.getStatusCode() == HttpStatus.NOT_FOUND
        );
    }
}