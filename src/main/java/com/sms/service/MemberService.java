package com.sms.service;

import com.sms.model.Member;
import com.sms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest((password + "sms_pro_salt_2026").getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return password;
        }
    }

    public Map<String, Object> register(String fullName, String email, String phone, String department, String password) {
        Map<String, Object> result = new HashMap<>();

        if (memberRepository.existsByEmail(email.toLowerCase())) {
            result.put("success", false);
            result.put("message", "Member with this email already exists");
            return result;
        }

        Member member = new Member();
        member.setFullName(fullName);
        member.setEmail(email.toLowerCase());
        member.setPhone(phone);
        member.setDepartment(department);
        member.setPasswordHash(hashPassword(password));
        member.setStatus("PENDING");

        Member saved = memberRepository.save(member);
        result.put("success", true);
        result.put("message", "Registration submitted. Waiting for admin approval.");
        result.put("member", safeMember(saved));
        return result;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> result = new HashMap<>();

        Member member = memberRepository.findByEmail(email.toLowerCase()).orElse(null);
        if (member == null) {
            result.put("success", false);
            result.put("message", "Member not found. Please register first.");
            return result;
        }

        if ("PENDING".equals(member.getStatus())) {
            result.put("success", false);
            result.put("message", "Your account is pending admin approval.");
            return result;
        }

        if ("REJECTED".equals(member.getStatus())) {
            result.put("success", false);
            result.put("message", "Your account has been rejected by admin.");
            return result;
        }

        if (!member.getPasswordHash().equals(hashPassword(password))) {
            result.put("success", false);
            result.put("message", "Invalid password");
            return result;
        }

        result.put("success", true);
        result.put("message", "Login successful");
        result.put("member", safeMember(member));
        return result;
    }

    public List<Map<String, Object>> getAllMembers() {
        return memberRepository.findAll().stream()
            .map(this::safeMember)
            .collect(java.util.stream.Collectors.toList());
    }

    public Map<String, Object> approveMember(String memberId, String adminId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) return Map.of("success", false, "message", "Member not found");

        member.setStatus("APPROVED");
        member.setApprovedBy(adminId);
        member.setApprovedAt(LocalDateTime.now());
        memberRepository.save(member);

        return Map.of("success", true, "message", "Member approved", "member", safeMember(member));
    }

    public Map<String, Object> rejectMember(String memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) return Map.of("success", false, "message", "Member not found");

        member.setStatus("REJECTED");
        memberRepository.save(member);

        return Map.of("success", true, "message", "Member rejected");
    }

    public Map<String, Object> deleteMember(String memberId) {
        if (!memberRepository.existsById(memberId))
            return Map.of("success", false, "message", "Member not found");
        memberRepository.deleteById(memberId);
        return Map.of("success", true, "message", "Member deleted");
    }

    public Map<String, Object> updatePermissions(String memberId, java.util.Map<String, Boolean> newPermissions) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) return Map.of("success", false, "message", "Member not found");

        // Merge new permissions into existing (only update keys provided)
        java.util.Map<String, Boolean> current = member.getPermissions();
        if (current == null) current = new java.util.HashMap<>();
        current.putAll(newPermissions);
        member.setPermissions(current);
        memberRepository.save(member);

        return Map.of("success", true, "message", "Permissions updated", "member", safeMember(member));
    }

    private Map<String, Object> safeMember(Member m) {
        Map<String, Object> safe = new HashMap<>();
        safe.put("id",          m.getId());
        safe.put("fullName",    m.getFullName());
        safe.put("email",       m.getEmail());
        safe.put("phone",       m.getPhone());
        safe.put("department",  m.getDepartment());
        safe.put("status",      m.getStatus());
        safe.put("approvedBy",  m.getApprovedBy());
        safe.put("createdAt",   m.getCreatedAt());
        safe.put("approvedAt",  m.getApprovedAt());
        // ←← THIS was the bug: permissions were saved but never returned!
        safe.put("permissions", m.getPermissions() != null ? m.getPermissions() : new java.util.HashMap<>());
        return safe;
    }
}
