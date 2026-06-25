package com.sms.controller;

import com.sms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // POST /api/admin/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String fullName = body.get("fullName");
        String email    = body.get("email");
        String password = body.get("password");

        if (email == null || email.isBlank())
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email is required"));
        if (password == null || password.length() < 6)
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Password must be at least 6 characters"));
        if (fullName == null || fullName.isBlank())
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Full name is required"));

        Map<String, Object> result = adminService.register(fullName, email, password);
        boolean success = (boolean) result.get("success");
        return success ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    // POST /api/admin/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email    = body.get("email");
        String password = body.get("password");

        if (email == null || password == null)
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email and password are required"));

        Map<String, Object> result = adminService.login(email, password);
        boolean success = (boolean) result.get("success");
        return success ? ResponseEntity.ok(result) : ResponseEntity.status(401).body(result);
    }

    // GET /api/admin/exists — check if any admin registered (for first-time setup)
    @GetMapping("/exists")
    public ResponseEntity<?> exists() {
        return ResponseEntity.ok(Map.of("exists", adminService.hasAnyAdmin()));
    }
}
