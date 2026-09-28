package com.example.attendance.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

public record SessionDTO(
        Long id,
        @NotNull LocalDate sessionDate,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime,
        @NotBlank String sessionType,
        @NotNull Long subjectId,
        String subjectCode,
        String subjectName
) {}
