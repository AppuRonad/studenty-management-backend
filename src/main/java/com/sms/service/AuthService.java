package com.sms.service;

import com.sms.model.Student;
import com.sms.model.StudentAuth;
import com.sms.repository.StudentAuthRepository;
import com.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired private StudentAuthRepository authRepository;
    @Autowired private StudentRepository studentRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ── Register ────────────────────────────────────────────────────────────
    public Map<String, Object> register(String studentId, String password) {
        String id = studentId.toUpperCase();

        // Student must exist in the students collection
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return Map.of("success", false, "message", "Student ID not found. Please check your ID.");
        }

        // Already registered
        if (authRepository.existsByStudentId(id)) {
            return Map.of("success", false, "message", "Account already exists for this Student ID.");
        }

        // Hash password and save
        String hash = encoder.encode(password);
        authRepository.save(new StudentAuth(id, hash));

        return Map.of("success", true, "message", "Account created successfully!", "studentId", id);
    }

    // ── Login ───────────────────────────────────────────────────────────────
    public Map<String, Object> login(String studentId, String password) {
        String id = studentId.toUpperCase();

        // Find auth record
        Optional<StudentAuth> auth = authRepository.findById(id);
        if (auth.isEmpty()) {
            return Map.of("success", false, "message", "No account found for this Student ID. Please register first.");
        }

        // Verify password
        if (!encoder.matches(password, auth.get().getPasswordHash())) {
            return Map.of("success", false, "message", "Incorrect password. Please try again.");
        }

        // Get student data
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return Map.of("success", false, "message", "Student record not found.");
        }

        return Map.of("success", true, "message", "Login successful", "student", student.get());
    }

    // ── Check if registered ─────────────────────────────────────────────────
    public boolean isRegistered(String studentId) {
        return authRepository.existsByStudentId(studentId.toUpperCase());
    }
}
