package com.example.attendance.service;

import com.example.attendance.dto.SubjectDTO;
import com.example.attendance.entity.Subject;
import com.example.attendance.exception.ResourceNotFoundException;
import com.example.attendance.repository.SubjectRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public SubjectDTO create(SubjectDTO dto) {
        if (repository.existsBySubjectCode(dto.subjectCode())) {
            throw new DataIntegrityViolationException("Subject code already exists");
        }
        return toDto(repository.save(new Subject(dto.subjectCode(), dto.subjectName(), dto.facultyName())));
    }

    public List<SubjectDTO> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public SubjectDTO findById(Long id) { return toDto(get(id)); }

    public SubjectDTO update(Long id, SubjectDTO dto) {
        Subject subject = get(id);
        subject.setSubjectCode(dto.subjectCode());
        subject.setSubjectName(dto.subjectName());
        subject.setFacultyName(dto.facultyName());
        return toDto(repository.save(subject));
    }

    public void delete(Long id) { repository.delete(get(id)); }

    public Subject get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + id));
    }

    private SubjectDTO toDto(Subject s) {
        return new SubjectDTO(s.getId(), s.getSubjectCode(), s.getSubjectName(), s.getFacultyName());
    }
}
