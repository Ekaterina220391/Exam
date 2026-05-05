package com.example.exam.controller;
import com.example.exam.model.Faculty;
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
class FacultyControllerTestResTemplate {

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
    void addFaculty() {
        String body = """
                {"name":"Math","color":"Blue"}
                """;

        ResponseEntity<Faculty> response = restTemplate.exchange(
                url("/faculty"),
                HttpMethod.POST,
                new HttpEntity<>(body, jsonHeaders()),
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Math", response.getBody().getName());
        assertEquals("Blue", response.getBody().getColor());
    }

    @Test
    void getAllFaculties() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url("/faculty"),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getFaculty() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url("/faculty/1"),
                String.class
        );

        assertTrue(
                response.getStatusCode() == HttpStatus.OK ||
                        response.getStatusCode() == HttpStatus.NOT_FOUND
        );
    }

    @Test
    void searchFaculties() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url("/faculty/search?text=Blue"),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateFaculty() {
        String body = """
                {"name":"History","color":"Red"}
                """;

        ResponseEntity<String> response = restTemplate.exchange(
                url("/faculty/1"),
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
    void deleteFaculty() {
        ResponseEntity<String> response = restTemplate.exchange(
                url("/faculty/1"),
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
    void getFacultyStudents() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url("/faculty/1/students"),
                String.class
        );

        assertTrue(
                response.getStatusCode() == HttpStatus.OK ||
                        response.getStatusCode() == HttpStatus.NOT_FOUND
        );
    }
}
