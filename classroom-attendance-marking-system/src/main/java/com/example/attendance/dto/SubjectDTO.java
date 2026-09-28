package com.example.attendance.dto;

import jakarta.validation.constraints.NotBlank;

public record SubjectDTO(
        Long id,
        @NotBlank String subjectCode,
        @NotBlank String subjectName,
        @NotBlank String facultyName
) {}
