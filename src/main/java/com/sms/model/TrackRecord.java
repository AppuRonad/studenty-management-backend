package com.sms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.List;

@Document(collection = "track_records")
public class TrackRecord {

    @Id
    private String id;

    @Indexed(unique = true)
    private String studentId;

    private List<SemesterRecord> semesters;
    private AttendanceSummary attendance;
    private AssignmentSummary assignments;
    private List<ProjectRecord> projects;
    private List<CertificationRecord> certifications;

    // ── Nested classes ──────────────────────────────────────────────────────

    public static class SemesterRecord {
        private String sem;
        private double gpa;
        private int credits;
        private List<String> subjects;
        private int attendance;

        public String getSem() { return sem; }
        public void setSem(String sem) { this.sem = sem; }
        public double getGpa() { return gpa; }
        public void setGpa(double gpa) { this.gpa = gpa; }
        public int getCredits() { return credits; }
        public void setCredits(int credits) { this.credits = credits; }
        public List<String> getSubjects() { return subjects; }
        public void setSubjects(List<String> subjects) { this.subjects = subjects; }
        public int getAttendance() { return attendance; }
        public void setAttendance(int attendance) { this.attendance = attendance; }
    }

    public static class AttendanceSummary {
        private int total;
        private int present;
        private int percent;

        public int getTotal() { return total; }
        public void setTotal(int total) { this.total = total; }
        public int getPresent() { return present; }
        public void setPresent(int present) { this.present = present; }
        public int getPercent() { return percent; }
        public void setPercent(int percent) { this.percent = percent; }
    }

    public static class AssignmentSummary {
        private int total;
        private int submitted;
        private int onTime;

        public int getTotal() { return total; }
        public void setTotal(int total) { this.total = total; }
        public int getSubmitted() { return submitted; }
        public void setSubmitted(int submitted) { this.submitted = submitted; }
        public int getOnTime() { return onTime; }
        public void setOnTime(int onTime) { this.onTime = onTime; }
    }

    public static class ProjectRecord {
        private String title;
        private String tech;
        private String grade;
        private int year;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getTech() { return tech; }
        public void setTech(String tech) { this.tech = tech; }
        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }
        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }
    }

    public static class CertificationRecord {
        private String name;
        private String issuer;
        private String date;
        private String badge;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getIssuer() { return issuer; }
        public void setIssuer(String issuer) { this.issuer = issuer; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getBadge() { return badge; }
        public void setBadge(String badge) { this.badge = badge; }
    }

    // ── Main getters/setters ────────────────────────────────────────────────

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public List<SemesterRecord> getSemesters() { return semesters; }
    public void setSemesters(List<SemesterRecord> semesters) { this.semesters = semesters; }
    public AttendanceSummary getAttendance() { return attendance; }
    public void setAttendance(AttendanceSummary attendance) { this.attendance = attendance; }
    public AssignmentSummary getAssignments() { return assignments; }
    public void setAssignments(AssignmentSummary assignments) { this.assignments = assignments; }
    public List<ProjectRecord> getProjects() { return projects; }
    public void setProjects(List<ProjectRecord> projects) { this.projects = projects; }
    public List<CertificationRecord> getCertifications() { return certifications; }
    public void setCertifications(List<CertificationRecord> certifications) { this.certifications = certifications; }
}
