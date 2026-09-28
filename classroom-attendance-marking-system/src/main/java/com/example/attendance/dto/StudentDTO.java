package com.example.attendance.dto;

import jakarta.validation.constraints.*;

public record StudentDTO(
        Long id,
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String rollNumber,
        @NotBlank String department,
        @NotNull @Positive Integer year,
        @NotBlank String section
) {}
