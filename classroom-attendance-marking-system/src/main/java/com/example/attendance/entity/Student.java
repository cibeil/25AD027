package com.example.attendance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "students", uniqueConstraints = @UniqueConstraint(name = "uk_student_roll_number", columnNames = "roll_number"))
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(name = "roll_number", nullable = false, unique = true)
    private String rollNumber;

    @NotBlank
    @Column(nullable = false)
    private String department;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer year;

    @NotBlank
    @Column(nullable = false)
    private String section;

    public Student() {}

    public Student(String name, String email, String rollNumber, String department, Integer year, String section) {
        this.name = name;
        this.email = email;
        this.rollNumber = rollNumber;
        this.department = department;
        this.year = year;
        this.section = section;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRollNumber() { return rollNumber; }
    public String getDepartment() { return department; }
    public Integer getYear() { return year; }
    public String getSection() { return section; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public void setDepartment(String department) { this.department = department; }
    public void setYear(Integer year) { this.year = year; }
    public void setSection(String section) { this.section = section; }
}
