package com.example.attendance.config;

import com.example.attendance.entity.*;
import com.example.attendance.enums.AttendanceStatus;
import com.example.attendance.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(StudentRepository students,
                                SubjectRepository subjects,
                                SessionRepository sessions,
                                AttendanceRepository attendance) {
        return args -> {
            if (students.count() > 0 || subjects.count() > 0 || sessions.count() > 0) return;

            Student s1 = students.save(new Student("Arun Kumar", "arun@gmail.com", "23AIDS001", "AI&DS", 3, "A"));
            Student s2 = students.save(new Student("Priya S", "priya@gmail.com", "23AIDS002", "AI&DS", 3, "A"));
            Student s3 = students.save(new Student("Rahul K", "rahul@gmail.com", "23AIDS003", "AI&DS", 3, "A"));
            Student s4 = students.save(new Student("Divya M", "divya@gmail.com", "23AIDS004", "AI&DS", 3, "A"));
            Student s5 = students.save(new Student("Karthik R", "karthik@gmail.com", "23AIDS005", "AI&DS", 3, "A"));

            Subject dbms = subjects.save(new Subject("CS301", "Database Management Systems", "Dr. Kumar"));
            Subject java = subjects.save(new Subject("CS302", "Java Programming", "Dr. Priya"));
            Subject ai = subjects.save(new Subject("AI303", "Artificial Intelligence", "Dr. Meena"));

            LocalDate d1 = LocalDate.now().minusDays(5);
            LocalDate d2 = LocalDate.now().minusDays(4);
            LocalDate d3 = LocalDate.now().minusDays(3);

            Session a = new Session();
            a.setSessionDate(d1); a.setStartTime(LocalTime.of(9,0)); a.setEndTime(LocalTime.of(10,0));
            a.setSessionType("LECTURE"); a.setSubject(dbms);
            a = sessions.save(a);

            Session b = new Session();
            b.setSessionDate(d2); b.setStartTime(LocalTime.of(10,0)); b.setEndTime(LocalTime.of(11,0));
            b.setSessionType("LECTURE"); b.setSubject(java);
            b = sessions.save(b);

            Session c = new Session();
            c.setSessionDate(d3); c.setStartTime(LocalTime.of(11,0)); c.setEndTime(LocalTime.of(12,0));
            c.setSessionType("LAB"); c.setSubject(ai);
            c = sessions.save(c);

            mark(attendance, s1, a, AttendanceStatus.PRESENT);
            mark(attendance, s1, b, AttendanceStatus.PRESENT);
            mark(attendance, s1, c, AttendanceStatus.PRESENT);

            mark(attendance, s2, a, AttendanceStatus.PRESENT);
            mark(attendance, s2, b, AttendanceStatus.ABSENT);
            mark(attendance, s2, c, AttendanceStatus.ABSENT);

            mark(attendance, s3, a, AttendanceStatus.PRESENT);
            mark(attendance, s3, b, AttendanceStatus.PRESENT);
            mark(attendance, s3, c, AttendanceStatus.ABSENT);

            mark(attendance, s4, a, AttendanceStatus.ABSENT);
            mark(attendance, s4, b, AttendanceStatus.ABSENT);
            mark(attendance, s4, c, AttendanceStatus.PRESENT);

            mark(attendance, s5, a, AttendanceStatus.PRESENT);
            mark(attendance, s5, b, AttendanceStatus.PRESENT);
            mark(attendance, s5, c, AttendanceStatus.PRESENT);
        };
    }

    private void mark(AttendanceRepository repo, Student student, Session session, AttendanceStatus status) {
        AttendanceRecord record = new AttendanceRecord();
        record.setStudent(student);
        record.setSession(session);
        record.setStatus(status);
        record.setRemarks(status == AttendanceStatus.PRESENT ? "Present in class" : "Absent");
        repo.save(record);
    }
}
