package com.example.attendance.entity;

import com.example.attendance.enums.AttendanceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_records",
       uniqueConstraints = @UniqueConstraint(name = "uk_attendance_student_session",
                                             columnNames = {"student_id", "session_id"}))
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    @Column(name = "marked_at", nullable = false)
    private LocalDateTime markedAt;

    @Column(length = 500)
    private String remarks;

    @PrePersist
    public void prePersist() {
        if (markedAt == null) markedAt = LocalDateTime.now();
    }

    public AttendanceRecord() {}

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public Session getSession() { return session; }
    public AttendanceStatus getStatus() { return status; }
    public LocalDateTime getMarkedAt() { return markedAt; }
    public String getRemarks() { return remarks; }

    public void setStudent(Student student) { this.student = student; }
    public void setSession(Session session) { this.session = session; }
    public void setStatus(AttendanceStatus status) { this.status = status; }
    public void setMarkedAt(LocalDateTime markedAt) { this.markedAt = markedAt; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
