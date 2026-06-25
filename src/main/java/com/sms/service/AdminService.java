package com.sms.service;

import com.sms.model.Admin;
import com.sms.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Simple SHA-256-style hash using Java built-ins (no Spring Security needed)
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest((password + "sms_pro_salt_2026").getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return password; // fallback
        }
    }

    public Map<String, Object> register(String fullName, String email, String password) {
        Map<String, Object> result = new HashMap<>();

        if (adminRepository.existsByEmail(email.toLowerCase())) {
            result.put("success", false);
            result.put("message", "Admin with this email already exists");
            return result;
        }

        Admin admin = new Admin();
        admin.setFullName(fullName);
        admin.setEmail(email.toLowerCase());
        admin.setPasswordHash(hashPassword(password));

        Admin saved = adminRepository.save(admin);
        result.put("success", true);
        result.put("message", "Admin registered successfully");
        result.put("admin", safeAdmin(saved));
        return result;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> result = new HashMap<>();

        Admin admin = adminRepository.findByEmail(email.toLowerCase()).orElse(null);
        if (admin == null) {
            result.put("success", false);
            result.put("message", "Admin not found");
            return result;
        }

        if (!admin.getPasswordHash().equals(hashPassword(password))) {
            result.put("success", false);
            result.put("message", "Invalid password");
            return result;
        }

        result.put("success", true);
        result.put("message", "Login successful");
        result.put("admin", safeAdmin(admin));
        return result;
    }

    // Return admin without password hash
    private Map<String, Object> safeAdmin(Admin admin) {
        Map<String, Object> safe = new HashMap<>();
        safe.put("id", admin.getId());
        safe.put("fullName", admin.getFullName());
        safe.put("email", admin.getEmail());
        safe.put("role", admin.getRole());
        safe.put("createdAt", admin.getCreatedAt());
        return safe;
    }

    public boolean hasAnyAdmin() {
        return adminRepository.count() > 0;
    }
}
