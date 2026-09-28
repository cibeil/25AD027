package com.example.attendance.dto;

import com.example.attendance.enums.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceResponseDTO(
        Long id,
        Long studentId,
        String studentName,
        String rollNumber,
        Long sessionId,
        LocalDate sessionDate,
        String subjectCode,
        AttendanceStatus status,
        LocalDateTime markedAt,
        String remarks
) {}
