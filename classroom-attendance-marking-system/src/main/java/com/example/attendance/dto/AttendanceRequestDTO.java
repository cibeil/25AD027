package com.example.attendance.dto;

import com.example.attendance.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;

public record AttendanceRequestDTO(
        @NotNull Long studentId,
        @NotNull Long sessionId,
        @NotNull AttendanceStatus status,
        String remarks
) {}
