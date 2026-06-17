package com.example.exam.service;

import com.example.exam.model.Faculty;
import com.example.exam.model.Student;
import com.example.exam.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    // Добавить факультет
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    // Найти факультет
    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method for find faculty by id = {}", id);
        return facultyRepository.findById(id).orElseGet(() -> {
            logger.error("There is no faculty with id = {}", id);
            return null;
        });
    }

    // Получить все факультеты
    public List<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }

    // Обновить факультет
    public Faculty updateFaculty(Long id, Faculty faculty) {
        logger.info("Was invoked method for update faculty with id = {}", id);
        Faculty existingFaculty = facultyRepository.findById(id).orElse(null);

        if (existingFaculty == null) {
            logger.error("Cannot update: there is no faculty with id = {}", id);
            return null;
        }

        existingFaculty.setName(faculty.getName());
        existingFaculty.setColor(faculty.getColor());
        return facultyRepository.save(existingFaculty);
    }


    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty with id = {}", id);
        facultyRepository.deleteById(id);
    }


    public List<Faculty> searchByNameOrColor(String query) {
        logger.info("Was invoked method for search faculty by name or color containing: {}", query);
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(query, query);
    }


    public Collection<Student> getStudentsByFacultyId(Long id) {
        logger.info("Was invoked method to get students for faculty id = {}", id);

        return facultyRepository.findById(id)
                .map(faculty -> {
                    Collection<Student> students = faculty.getStudents();
                    logger.debug("Found {} students for faculty id = {}", students.size(), id);
                    return students;
                })
                .orElseGet(() -> {
                    logger.error("Could not find students: faculty with id = {} does not exist", id);
                    return Collections.emptyList();
                });
    }


    public String getLongestFacultyName() {
        logger.info("Was invoked method to get longest faculty name");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }
}