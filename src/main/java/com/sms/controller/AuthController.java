package com.sms.controller;

import com.sms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    // POST /api/auth/register
    // Body: { "studentId": "STU001", "password": "mypassword" }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String studentId = body.get("studentId");
        String password  = body.get("password");

        if (studentId == null || studentId.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Student ID is required"));
        }
        if (password == null || password.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Password must be at least 6 characters"));
        }

        Map<String, Object> result = authService.register(studentId, password);
        boolean success = (boolean) result.get("success");
        return success
            ? ResponseEntity.ok(result)
            : ResponseEntity.badRequest().body(result);
    }

    // POST /api/auth/login
    // Body: { "studentId": "STU001", "password": "mypassword" }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String studentId = body.get("studentId");
        String password  = body.get("password");

        if (studentId == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Student ID and password are required"));
        }

        Map<String, Object> result = authService.login(studentId, password);
        boolean success = (boolean) result.get("success");
        return success
            ? ResponseEntity.ok(result)
            : ResponseEntity.status(401).body(result);
    }

    // GET /api/auth/check/{studentId}
    @GetMapping("/check/{studentId}")
    public ResponseEntity<?> checkRegistered(@PathVariable String studentId) {
        boolean registered = authService.isRegistered(studentId);
        return ResponseEntity.ok(Map.of("registered", registered, "studentId", studentId.toUpperCase()));
    }
}
