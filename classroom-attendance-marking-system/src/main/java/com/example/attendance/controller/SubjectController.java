package com.example.attendance.controller;

import com.example.attendance.dto.SubjectDTO;
import com.example.attendance.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService service;

    public SubjectController(SubjectService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDTO create(@Valid @RequestBody SubjectDTO dto) { return service.create(dto); }

    @GetMapping
    public List<SubjectDTO> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public SubjectDTO one(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public SubjectDTO update(@PathVariable Long id, @Valid @RequestBody SubjectDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
