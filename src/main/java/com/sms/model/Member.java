package com.sms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "members")
public class Member {

    @Id
    private String id;

    private String fullName;

    @Indexed(unique = true)
    private String email;

    private String passwordHash;

    private String department; // Member is locked to ONE department

    private String phone;

    // PENDING = waiting for admin approval
    // APPROVED = can login
    // REJECTED = denied
    private String status = "PENDING";

    private String approvedBy;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime approvedAt;

    /**
     * Granular permission map — admin toggles each permission per member.
     * All default to FALSE (denied). Admin must explicitly enable.
     *
     * Keys:
     *  viewStudents    — can view department students list
     *  addStudents     — can add new students to their dept
     *  editStudents    — can edit student details in their dept
     *  deleteStudents  — can delete students in their dept
     *  viewAnalytics   — can view analytics for their dept
     *  addMarks        — can add/edit marks & track records
     *  addAttendance   — can add attendance records
     *  viewCompetitions— can view competition records
     *  addCompetitions — can add competition entries
     *  messaging       — can send messages to students
     */
    private Map<String, Boolean> permissions = new HashMap<>(Map.of(
        "viewStudents",     false,
        "addStudents",      false,
        "editStudents",     false,
        "deleteStudents",   false,
        "viewAnalytics",    false,
        "addMarks",         false,
        "addAttendance",    false,
        "viewCompetitions", false,
        "addCompetitions",  false,
        "messaging",        false
    ));

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }

    public Map<String, Boolean> getPermissions() { return permissions; }
    public void setPermissions(Map<String, Boolean> permissions) { this.permissions = permissions; }
}
