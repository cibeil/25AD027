package com.example.attendance.service;

import com.example.attendance.dto.SessionDTO;
import com.example.attendance.entity.Session;
import com.example.attendance.entity.Subject;
import com.example.attendance.exception.InvalidAttendanceException;
import com.example.attendance.exception.ResourceNotFoundException;
import com.example.attendance.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class SessionService {
    private final SessionRepository repository;
    private final SubjectService subjectService;

    public SessionService(SessionRepository repository, SubjectService subjectService) {
        this.repository = repository;
        this.subjectService = subjectService;
    }

    public SessionDTO create(SessionDTO dto) {
        validateTimes(dto.startTime(), dto.endTime());
        Subject subject = subjectService.get(dto.subjectId());

        Session session = new Session();
        session.setSessionDate(dto.sessionDate());
        session.setStartTime(dto.startTime());
        session.setEndTime(dto.endTime());
        session.setSessionType(dto.sessionType().toUpperCase());
        session.setSubject(subject);

        return toDto(repository.save(session));
    }

    public List<SessionDTO> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public SessionDTO findById(Long id) { return toDto(get(id)); }

    public SessionDTO update(Long id, SessionDTO dto) {
        validateTimes(dto.startTime(), dto.endTime());
        Session session = get(id);
        session.setSessionDate(dto.sessionDate());
        session.setStartTime(dto.startTime());
        session.setEndTime(dto.endTime());
        session.setSessionType(dto.sessionType().toUpperCase());
        session.setSubject(subjectService.get(dto.subjectId()));
        return toDto(repository.save(session));
    }

    public void delete(Long id) { repository.delete(get(id)); }

    public Session get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found: " + id));
    }

    private void validateTimes(LocalTime start, LocalTime end) {
        if (!start.isBefore(end)) {
            throw new InvalidAttendanceException("Session end time must be after start time.");
        }
    }

    private SessionDTO toDto(Session s) {
        return new SessionDTO(s.getId(), s.getSessionDate(), s.getStartTime(), s.getEndTime(),
                s.getSessionType(), s.getSubject().getId(), s.getSubject().getSubjectCode(),
                s.getSubject().getSubjectName());
    }
}
