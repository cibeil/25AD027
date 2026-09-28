package com.example.attendance.controller;

import com.example.attendance.dto.SessionDTO;
import com.example.attendance.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    private final SessionService service;

    public SessionController(SessionService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDTO create(@Valid @RequestBody SessionDTO dto) { return service.create(dto); }

    @GetMapping
    public List<SessionDTO> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public SessionDTO one(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public SessionDTO update(@PathVariable Long id, @Valid @RequestBody SessionDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
