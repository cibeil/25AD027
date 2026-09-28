# Classroom Attendance Marking System

Spring Boot + Java 17 + MySQL project implementing the Classroom Attendance Marking System requirements.

## Features
- Student, subject and session CRUD APIs
- Attendance marking with duplicate protection
- PRESENT / ABSENT enum
- Attendance percentage calculation
- Configurable 75% minimum threshold
- ELIGIBLE / SHORTAGE status
- Past-session attendance correction
- Date/status filtering
- Dashboard API
- Jakarta validation
- Global exception handling
- MySQL + Spring Data JPA
- Simple browser dashboard at `/`

## Run

1. Install Java 17+ and Maven.
2. Create MySQL database if desired:
   `CREATE DATABASE attendance_db;`
3. Set environment variables:
   - `DB_USERNAME`
   - `DB_PASSWORD`
   - optional `DB_URL`
   - optional `ATTENDANCE_MINIMUM_PERCENTAGE`
4. Run:
   `mvn spring-boot:run`
5. Open:
   `http://localhost:7000/`

If you do not set DB_PASSWORD, edit `src/main/resources/application.properties` and replace `YOUR_PASSWORD`.

## Important
Do not commit real database passwords or other secrets to Git.

## Main APIs
- POST/GET/PUT/DELETE `/api/students`
- POST/GET/PUT/DELETE `/api/subjects`
- POST/GET/PUT/DELETE `/api/sessions`
- POST/GET/PUT/DELETE `/api/attendance`
- GET `/api/attendance/student/{studentId}`
- GET `/api/attendance/student/{studentId}/percentage`
- GET `/api/attendance/shortage`
- GET `/api/attendance/filter?date=YYYY-MM-DD&status=PRESENT`
- PUT `/api/attendance/{id}/correct`
- GET `/api/dashboard`

The application seeds 5 students, 3 subjects, 3 past sessions and attendance data on an empty database.
