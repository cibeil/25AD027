package com.example.attendance.dto;

public record DashboardDTO(
        long totalStudents,
        long totalSubjects,
        long totalSessions,
        long totalAttendanceRecords,
        long presentCount,
        long absentCount,
        long shortageStudents,
        long eligibleStudents
) {}
