package com.example.attendance.service;

import com.example.attendance.config.AttendanceConfig;
import com.example.attendance.dto.AttendanceRequestDTO;
import com.example.attendance.dto.AttendanceResponseDTO;
import com.example.attendance.dto.AttendanceSummaryDTO;
import com.example.attendance.entity.AttendanceRecord;
import com.example.attendance.entity.Session;
import com.example.attendance.entity.Student;
import com.example.attendance.enums.AttendanceStatus;
import com.example.attendance.exception.DuplicateAttendanceException;
import com.example.attendance.exception.InvalidAttendanceException;
import com.example.attendance.exception.ResourceNotFoundException;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.StudentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentService studentService;
    private final SessionService sessionService;
    private final StudentRepository studentRepository;
    private final AttendanceConfig config;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             StudentService studentService,
                             SessionService sessionService,
                             StudentRepository studentRepository,
                             AttendanceConfig config) {
        this.attendanceRepository = attendanceRepository;
        this.studentService = studentService;
        this.sessionService = sessionService;
        this.studentRepository = studentRepository;
        this.config = config;
    }

    public AttendanceResponseDTO create(AttendanceRequestDTO dto) {
        if (attendanceRepository.existsByStudentIdAndSessionId(dto.studentId(), dto.sessionId())) {
            throw new DuplicateAttendanceException("Attendance already marked for this student for this session.");
        }

        Student student = studentService.get(dto.studentId());
        Session session = sessionService.get(dto.sessionId());

        AttendanceRecord record = new AttendanceRecord();
        record.setStudent(student);
        record.setSession(session);
        record.setStatus(dto.status());
        record.setRemarks(dto.remarks());

        try {
            return toDto(attendanceRepository.save(record));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateAttendanceException("Attendance already marked for this student for this session.");
        }
    }

    public List<AttendanceResponseDTO> findAll() {
        return attendanceRepository.findAll().stream().map(this::toDto).toList();
    }

    public AttendanceResponseDTO findById(Long id) {
        return toDto(get(id));
    }

    public AttendanceResponseDTO update(Long id, AttendanceRequestDTO dto) {
        AttendanceRecord record = get(id);
        ensurePastSession(record.getSession());
        if (!record.getStudent().getId().equals(dto.studentId()) ||
                !record.getSession().getId().equals(dto.sessionId())) {
            throw new InvalidAttendanceException("Student and session cannot be changed during attendance correction.");
        }
        record.setStatus(dto.status());
        record.setRemarks(dto.remarks());
        return toDto(attendanceRepository.save(record));
    }

    public AttendanceResponseDTO correct(Long id, AttendanceRequestDTO dto) {
        return update(id, dto);
    }

    public void delete(Long id) {
        attendanceRepository.delete(get(id));
    }

    public List<AttendanceResponseDTO> byStudent(Long studentId) {
        studentService.get(studentId);
        return attendanceRepository.findByStudentId(studentId).stream().map(this::toDto).toList();
    }

    public AttendanceSummaryDTO summary(Long studentId) {
        Student student = studentService.get(studentId);
        List<AttendanceRecord> records = attendanceRepository.findByStudentId(studentId);
        long total = records.size();
        long present = records.stream().filter(r -> r.getStatus() == AttendanceStatus.PRESENT).count();
        long absent = total - present;
        double percentage = total == 0 ? 0.0 : (present * 100.0) / total;
        percentage = Math.round(percentage * 100.0) / 100.0;
        String status = percentage >= config.getMinimumPercentage() ? "ELIGIBLE" : "SHORTAGE";

        return new AttendanceSummaryDTO(student.getId(), student.getName(), student.getRollNumber(),
                total, present, absent, percentage, status);
    }

    public List<AttendanceSummaryDTO> shortageStudents() {
        return studentRepository.findAll().stream()
                .map(s -> summary(s.getId()))
                .filter(s -> "SHORTAGE".equals(s.status()))
                .toList();
    }

    public List<AttendanceResponseDTO> filter(LocalDate date, AttendanceStatus status) {
        if (date != null && status != null) {
            return attendanceRepository.findBySessionSessionDateAndStatus(date, status)
                    .stream().map(this::toDto).toList();
        }
        if (date != null) {
            return attendanceRepository.findBySessionSessionDate(date)
                    .stream().map(this::toDto).toList();
        }
        if (status != null) {
            return attendanceRepository.findByStatus(status)
                    .stream().map(this::toDto).toList();
        }
        return findAll();
    }

    public long count(AttendanceStatus status) {
        return attendanceRepository.countByStatus(status);
    }

    public long totalCount() {
        return attendanceRepository.count();
    }

    private void ensurePastSession(Session session) {
        if (!session.getSessionDate().isBefore(LocalDate.now())) {
            throw new InvalidAttendanceException("Attendance can only be corrected for past sessions.");
        }
    }

    private AttendanceRecord get(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found: " + id));
    }

    private AttendanceResponseDTO toDto(AttendanceRecord r) {
        return new AttendanceResponseDTO(
                r.getId(),
                r.getStudent().getId(),
                r.getStudent().getName(),
                r.getStudent().getRollNumber(),
                r.getSession().getId(),
                r.getSession().getSessionDate(),
                r.getSession().getSubject().getSubjectCode(),
                r.getStatus(),
                r.getMarkedAt(),
                r.getRemarks()
        );
    }
}
