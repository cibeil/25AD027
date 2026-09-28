package com.example.attendance.controller;

import com.example.attendance.dto.AttendanceRequestDTO;
import com.example.attendance.dto.AttendanceResponseDTO;
import com.example.attendance.dto.AttendanceSummaryDTO;
import com.example.attendance.enums.AttendanceStatus;
import com.example.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService service;

    public AttendanceController(AttendanceService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceResponseDTO create(@Valid @RequestBody AttendanceRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<AttendanceResponseDTO> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public AttendanceResponseDTO one(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public AttendanceResponseDTO update(@PathVariable Long id, @Valid @RequestBody AttendanceRequestDTO dto) {
        return service.update(id, dto);
    }

    @PutMapping("/{id}/correct")
    public AttendanceResponseDTO correct(@PathVariable Long id, @Valid @RequestBody AttendanceRequestDTO dto) {
        return service.correct(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping("/student/{studentId}")
    public List<AttendanceResponseDTO> byStudent(@PathVariable Long studentId) {
        return service.byStudent(studentId);
    }

    @GetMapping("/student/{studentId}/percentage")
    public AttendanceSummaryDTO percentage(@PathVariable Long studentId) {
        return service.summary(studentId);
    }

    @GetMapping("/shortage")
    public List<AttendanceSummaryDTO> shortage() { return service.shortageStudents(); }

    @GetMapping("/filter")
    public List<AttendanceResponseDTO> filter(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) AttendanceStatus status) {
        return service.filter(date, status);
    }
}
