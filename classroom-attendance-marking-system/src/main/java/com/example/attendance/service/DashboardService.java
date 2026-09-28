package com.example.attendance.service;

import com.example.attendance.config.AttendanceConfig;
import com.example.attendance.dto.DashboardDTO;
import com.example.attendance.entity.Student;
import com.example.attendance.enums.AttendanceStatus;
import com.example.attendance.repository.SessionRepository;
import com.example.attendance.repository.StudentRepository;
import com.example.attendance.repository.SubjectRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final SessionRepository sessionRepository;
    private final AttendanceService attendanceService;
    private final AttendanceConfig config;

    public DashboardService(StudentRepository studentRepository,
                            SubjectRepository subjectRepository,
                            SessionRepository sessionRepository,
                            AttendanceService attendanceService,
                            AttendanceConfig config) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.sessionRepository = sessionRepository;
        this.attendanceService = attendanceService;
        this.config = config;
    }

    public DashboardDTO getDashboard() {
        long shortage = 0;
        long eligible = 0;
        for (Student student : studentRepository.findAll()) {
            double percentage = attendanceService.summary(student.getId()).attendancePercentage();
            if (percentage >= config.getMinimumPercentage()) eligible++;
            else shortage++;
        }

        return new DashboardDTO(
                studentRepository.count(),
                subjectRepository.count(),
                sessionRepository.count(),
                attendanceService.totalCount(),
                attendanceService.count(AttendanceStatus.PRESENT),
                attendanceService.count(AttendanceStatus.ABSENT),
                shortage,
                eligible
        );
    }
}
