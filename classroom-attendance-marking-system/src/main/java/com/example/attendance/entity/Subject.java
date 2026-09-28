package com.example.attendance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "subjects", uniqueConstraints = @UniqueConstraint(name = "uk_subject_code", columnNames = "subject_code"))
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "subject_code", nullable = false, unique = true)
    private String subjectCode;

    @NotBlank
    @Column(nullable = false)
    private String subjectName;

    @NotBlank
    @Column(nullable = false)
    private String facultyName;

    public Subject() {}

    public Subject(String subjectCode, String subjectName, String facultyName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.facultyName = facultyName;
    }

    public Long getId() { return id; }
    public String getSubjectCode() { return subjectCode; }
    public String getSubjectName() { return subjectName; }
    public String getFacultyName() { return facultyName; }

    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }
}
