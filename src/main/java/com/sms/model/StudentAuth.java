package com.sms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "student_auth")
public class StudentAuth {

    @Id
    private String studentId;   // studentId IS the primary key

    private String passwordHash; // BCrypt hashed password

    public StudentAuth() {}

    public StudentAuth(String studentId, String passwordHash) {
        this.studentId    = studentId;
        this.passwordHash = passwordHash;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
