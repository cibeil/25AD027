package com.example.attendance.controller;

import com.example.attendance.dto.StudentDTO;
import com.example.attendance.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO create(@Valid @RequestBody StudentDTO dto) { return service.create(dto); }

    @GetMapping
    public List<StudentDTO> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public StudentDTO one(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public StudentDTO update(@PathVariable Long id, @Valid @RequestBody StudentDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
