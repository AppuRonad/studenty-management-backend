package com.sms.controller;

import com.sms.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // POST /api/member/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String fullName   = body.get("fullName");
        String email      = body.get("email");
        String phone      = body.get("phone");
        String department = body.get("department");
        String password   = body.get("password");

        if (email == null || email.isBlank())
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email is required"));
        if (password == null || password.length() < 6)
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Password must be at least 6 characters"));
        if (department == null || department.isBlank())
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Department is required"));

        Map<String, Object> result = memberService.register(fullName, email, phone, department, password);
        boolean success = (boolean) result.get("success");
        return success ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    // POST /api/member/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email    = body.get("email");
        String password = body.get("password");

        if (email == null || password == null)
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email and password are required"));

        Map<String, Object> result = memberService.login(email, password);
        boolean success = (boolean) result.get("success");
        return success ? ResponseEntity.ok(result) : ResponseEntity.status(401).body(result);
    }

    // GET /api/member — get all members (admin only)
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    // PUT /api/member/{id}/approve
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable String id, @RequestBody(required = false) Map<String, String> body) {
        String adminId = body != null ? body.get("adminId") : "admin";
        Map<String, Object> result = memberService.approveMember(id, adminId);
        boolean success = (boolean) result.get("success");
        return success ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    // PUT /api/member/{id}/reject
    @PutMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable String id) {
        Map<String, Object> result = memberService.rejectMember(id);
        boolean success = (boolean) result.get("success");
        return success ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    // DELETE /api/member/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Map<String, Object> result = memberService.deleteMember(id);
        boolean success = (boolean) result.get("success");
        return success ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    // PUT /api/member/{id}/permissions
    // Body: { "viewStudents": true, "addStudents": false, ... }
    @PutMapping("/{id}/permissions")
    public ResponseEntity<?> updatePermissions(
            @PathVariable String id,
            @RequestBody java.util.Map<String, Boolean> permissions) {
        Map<String, Object> result = memberService.updatePermissions(id, permissions);
        boolean success = (boolean) result.get("success");
        return success ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}
