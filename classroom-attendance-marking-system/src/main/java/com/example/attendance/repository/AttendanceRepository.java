package com.example.attendance.repository;

import com.example.attendance.entity.AttendanceRecord;
import com.example.attendance.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {
    boolean existsByStudentIdAndSessionId(Long studentId, Long sessionId);
    List<AttendanceRecord> findByStudentId(Long studentId);
    List<AttendanceRecord> findBySessionSessionDate(LocalDate date);
    List<AttendanceRecord> findByStatus(AttendanceStatus status);
    List<AttendanceRecord> findBySessionSessionDateAndStatus(LocalDate date, AttendanceStatus status);
    long countByStatus(AttendanceStatus status);
    Optional<AttendanceRecord> findByStudentIdAndSessionId(Long studentId, Long sessionId);
}
