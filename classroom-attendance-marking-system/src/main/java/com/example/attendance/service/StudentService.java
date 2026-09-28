package com.example.attendance.service;

import com.example.attendance.dto.StudentDTO;
import com.example.attendance.entity.Student;
import com.example.attendance.exception.ResourceNotFoundException;
import com.example.attendance.repository.StudentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public StudentDTO create(StudentDTO dto) {
        if (repository.existsByRollNumber(dto.rollNumber())) {
            throw new DataIntegrityViolationException("Roll number already exists");
        }
        Student saved = repository.save(new Student(dto.name(), dto.email(), dto.rollNumber(),
                dto.department(), dto.year(), dto.section()));
        return toDto(saved);
    }

    public List<StudentDTO> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public StudentDTO findById(Long id) {
        return toDto(get(id));
    }

    public StudentDTO update(Long id, StudentDTO dto) {
        Student student = get(id);
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setRollNumber(dto.rollNumber());
        student.setDepartment(dto.department());
        student.setYear(dto.year());
        student.setSection(dto.section());
        return toDto(repository.save(student));
    }

    public void delete(Long id) {
        repository.delete(get(id));
    }

    public Student get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
    }

    private StudentDTO toDto(Student s) {
        return new StudentDTO(s.getId(), s.getName(), s.getEmail(), s.getRollNumber(),
                s.getDepartment(), s.getYear(), s.getSection());
    }
}
