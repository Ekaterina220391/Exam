package com.example.exam.controller;
import com.example.exam.model.Faculty;
import com.example.exam.service.FacultyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerTestMockMvc {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacultyService facultyService;

    @Test
    void addFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Math");
        faculty.setColor("Blue");

        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Math","color":"Blue"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Math"))
                .andExpect(jsonPath("$.color").value("Blue"));
    }

    @Test
    void getAllFaculties() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Math");
        faculty.setColor("Blue");

        when(facultyService.getAllFaculties()).thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Math"))
                .andExpect(jsonPath("$[0].color").value("Blue"));
    }

    @Test
    void getFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Math");
        faculty.setColor("Blue");

        when(facultyService.findFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Math"))
                .andExpect(jsonPath("$.color").value("Blue"));
    }

    @Test
    void searchFaculties() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Math");
        faculty.setColor("Blue");

        when(facultyService.searchByNameOrColor("Blue")).thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculty/search")
                        .param("text", "Blue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Math"))
                .andExpect(jsonPath("$[0].color").value("Blue"));
    }

    @Test
    void updateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("History");
        faculty.setColor("Red");

        when(facultyService.updateFaculty(eq(1L), any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(put("/faculty/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"History","color":"Red"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("History"))
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    void deleteFaculty() throws Exception {
        doNothing().when(facultyService).deleteFaculty(1L);

        mockMvc.perform(delete("/faculty/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getFacultyStudents() throws Exception {
        when(facultyService.getFacultyStudents(1L)).thenReturn(List.of());

        mockMvc.perform(get("/faculty/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}