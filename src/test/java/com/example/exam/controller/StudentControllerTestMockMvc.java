package com.example.exam.controller;
import com.example.exam.repository.FacultyRepository;
import com.example.exam.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTestMockMvc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void addStudent() throws Exception {
        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Anna","age":20}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Anna")))
                .andExpect(jsonPath("$.age", is(20)));
    }

    @Test
    void getAllStudents() throws Exception {
        mockMvc.perform(get("/student"))
                .andExpect(status().isOk());
    }

    @Test
    void getStudent() throws Exception {
        mockMvc.perform(get("/student/1"))
                .andExpect(result -> {
                    int code = result.getResponse().getStatus();
                    org.junit.jupiter.api.Assertions.assertTrue(code == 200 || code == 404);
                });
    }

    @Test
    void updateStudent() throws Exception {
        mockMvc.perform(put("/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Kate","age":21}
                                """))
                .andExpect(result -> {
                    int code = result.getResponse().getStatus();
                    org.junit.jupiter.api.Assertions.assertTrue(code == 200 || code == 404);
                });
    }

    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete("/student/1"))
                .andExpect(result -> {
                    int code = result.getResponse().getStatus();
                    org.junit.jupiter.api.Assertions.assertTrue(code == 200 || code == 404);
                });
    }

    @Test
    void getStudentsByAgeBetween() throws Exception {
        mockMvc.perform(get("/student/by-age")
                        .param("min", "18")
                        .param("max", "25"))
                .andExpect(status().isOk());
    }

    @Test
    void getStudentFaculty() throws Exception {
        mockMvc.perform(get("/student/1/faculty"))
                .andExpect(result -> {
                    int code = result.getResponse().getStatus();
                    org.junit.jupiter.api.Assertions.assertTrue(code == 200 || code == 404);
                });
    }
}
