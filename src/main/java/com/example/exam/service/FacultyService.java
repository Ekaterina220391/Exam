package com.example.exam.service;

import com.example.exam.model.Faculty;
import com.example.exam.repository.FacultyRepository;
import com.example.exam.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
    public Faculty updateFaculty(Long id, Faculty faculty) {
        Faculty existingFaculty = facultyRepository.findById(id).orElse(null);
        if (existingFaculty == null) {
            return null;
        }

        existingFaculty.setName(faculty.getName());
        existingFaculty.setColor(faculty.getColor());

        return facultyRepository.save(existingFaculty);
    }
}
