package com.example.attendance.dto;

public record AttendanceSummaryDTO(
        Long studentId,
        String studentName,
        String rollNumber,
        long totalSessions,
        long presentSessions,
        long absentSessions,
        double attendancePercentage,
        String status
) {}
