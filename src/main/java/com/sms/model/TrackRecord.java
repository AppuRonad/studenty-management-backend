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

    private List<SemesterRecord>    semesters;
    private AttendanceSummary       attendance;
    private AssignmentSummary       assignments;
    private List<ProjectRecord>     projects;
    private List<CertificationRecord> certifications;
    private List<InternshipRecord>  internships;
    private List<ExamResult>        examResults;
    private List<YearCgpa>          yearCgpas;
    private AdminMarks              adminMarks;

    // ── SemesterRecord ───────────────────────────────────────────────────────
    public static class SemesterRecord {
        private String sem;
        private double gpa;         // SGPA (1-10)
        private int credits;
        private List<String> subjects;
        private int attendance;
        // Admin-set fields
        private String grade;       // e.g. "A+", "B", "C"
        private String status;      // "PASS" | "FAIL"
        private Integer marks;      // out of 100

        public String getSem()    { return sem; }
        public void  setSem(String v) { this.sem = v; }
        public double getGpa()    { return gpa; }
        public void  setGpa(double v) { this.gpa = v; }
        public int   getCredits() { return credits; }
        public void  setCredits(int v) { this.credits = v; }
        public List<String> getSubjects() { return subjects; }
        public void  setSubjects(List<String> v) { this.subjects = v; }
        public int   getAttendance() { return attendance; }
        public void  setAttendance(int v) { this.attendance = v; }
        public String getGrade()  { return grade; }
        public void  setGrade(String v) { this.grade = v; }
        public String getStatus() { return status; }
        public void  setStatus(String v) { this.status = v; }
        public Integer getMarks() { return marks; }
        public void  setMarks(Integer v) { this.marks = v; }
    }

    // ── AttendanceSummary ────────────────────────────────────────────────────
    public static class AttendanceSummary {
        private int total;
        private int present;
        private int percent;

        public int  getTotal()   { return total; }
        public void setTotal(int v) { this.total = v; }
        public int  getPresent() { return present; }
        public void setPresent(int v) { this.present = v; }
        public int  getPercent() { return percent; }
        public void setPercent(int v) { this.percent = v; }
    }

    // ── AssignmentSummary ────────────────────────────────────────────────────
    public static class AssignmentSummary {
        private int total;
        private int submitted;
        private int onTime;

        public int  getTotal()     { return total; }
        public void setTotal(int v) { this.total = v; }
        public int  getSubmitted() { return submitted; }
        public void setSubmitted(int v) { this.submitted = v; }
        public int  getOnTime()    { return onTime; }
        public void setOnTime(int v) { this.onTime = v; }
    }

    // ── ProjectRecord ────────────────────────────────────────────────────────
    public static class ProjectRecord {
        private String title;
        private String description;
        private String tech;
        private String grade;
        private int year;

        public String getTitle()       { return title; }
        public void   setTitle(String v) { this.title = v; }
        public String getDescription() { return description; }
        public void   setDescription(String v) { this.description = v; }
        public String getTech()        { return tech; }
        public void   setTech(String v) { this.tech = v; }
        public String getGrade()       { return grade; }
        public void   setGrade(String v) { this.grade = v; }
        public int    getYear()        { return year; }
        public void   setYear(int v)   { this.year = v; }
    }

    // ── CertificationRecord ──────────────────────────────────────────────────
    public static class CertificationRecord {
        private String name;
        private String issuer;
        private String date;
        private String badge;
        private String fileUrl;   // optional upload link

        public String getName()    { return name; }
        public void   setName(String v) { this.name = v; }
        public String getIssuer()  { return issuer; }
        public void   setIssuer(String v) { this.issuer = v; }
        public String getDate()    { return date; }
        public void   setDate(String v) { this.date = v; }
        public String getBadge()   { return badge; }
        public void   setBadge(String v) { this.badge = v; }
        public String getFileUrl() { return fileUrl; }
        public void   setFileUrl(String v) { this.fileUrl = v; }
    }

    // ── InternshipRecord ─────────────────────────────────────────────────────
    public static class InternshipRecord {
        private String company;
        private String role;
        private int    durationMonths;
        private String startDate;
        private String endDate;
        private String description;
        private String stipend;     // optional

        public String getCompany()         { return company; }
        public void   setCompany(String v) { this.company = v; }
        public String getRole()            { return role; }
        public void   setRole(String v)    { this.role = v; }
        public int    getDurationMonths()  { return durationMonths; }
        public void   setDurationMonths(int v) { this.durationMonths = v; }
        public String getStartDate()       { return startDate; }
        public void   setStartDate(String v) { this.startDate = v; }
        public String getEndDate()         { return endDate; }
        public void   setEndDate(String v) { this.endDate = v; }
        public String getDescription()     { return description; }
        public void   setDescription(String v) { this.description = v; }
        public String getStipend()         { return stipend; }
        public void   setStipend(String v) { this.stipend = v; }
    }

    // ── ExamResult ───────────────────────────────────────────────────────────
    public static class ExamResult {
        private String semester;    // "Sem 1", "Sem 2" ...
        private String examType;    // "Internal", "External", "Practical"
        private int    marksObtained;
        private int    maxMarks;
        private String subject;
        private String grade;
        private String status;      // "PASS" | "FAIL"

        public String getSemester()        { return semester; }
        public void   setSemester(String v) { this.semester = v; }
        public String getExamType()        { return examType; }
        public void   setExamType(String v) { this.examType = v; }
        public int    getMarksObtained()   { return marksObtained; }
        public void   setMarksObtained(int v) { this.marksObtained = v; }
        public int    getMaxMarks()        { return maxMarks; }
        public void   setMaxMarks(int v)   { this.maxMarks = v; }
        public String getSubject()         { return subject; }
        public void   setSubject(String v) { this.subject = v; }
        public String getGrade()           { return grade; }
        public void   setGrade(String v)   { this.grade = v; }
        public String getStatus()          { return status; }
        public void   setStatus(String v)  { this.status = v; }
    }

    // ── YearCgpa ─────────────────────────────────────────────────────────────
    public static class YearCgpa {
        private int    year;        // 1 / 2 / 3 / 4
        private double cgpa;        // 1-10
        private String academicYear; // "2022-23"

        public int    getYear()         { return year; }
        public void   setYear(int v)    { this.year = v; }
        public double getCgpa()         { return cgpa; }
        public void   setCgpa(double v) { this.cgpa = v; }
        public String getAcademicYear() { return academicYear; }
        public void   setAcademicYear(String v) { this.academicYear = v; }
    }

    // ── AdminMarks (set by admin only) ────────────────────────────────────────
    public static class AdminMarks {
        private String  overallGrade;   // "A+", "A", "B+", "B", "C", "F"
        private String  overallStatus;  // "PASS" | "FAIL"
        private Double  internalMarks;
        private Double  externalMarks;
        private String  remarks;
        private List<SemesterAdminMark> semesterMarks;

        public static class SemesterAdminMark {
            private String  sem;
            private Integer marks;
            private String  grade;
            private String  status;
            private Integer attendance;

            public String  getSem()        { return sem; }
            public void    setSem(String v) { this.sem = v; }
            public Integer getMarks()      { return marks; }
            public void    setMarks(Integer v) { this.marks = v; }
            public String  getGrade()      { return grade; }
            public void    setGrade(String v) { this.grade = v; }
            public String  getStatus()     { return status; }
            public void    setStatus(String v) { this.status = v; }
            public Integer getAttendance() { return attendance; }
            public void    setAttendance(Integer v) { this.attendance = v; }
        }

        public String  getOverallGrade()  { return overallGrade; }
        public void    setOverallGrade(String v) { this.overallGrade = v; }
        public String  getOverallStatus() { return overallStatus; }
        public void    setOverallStatus(String v) { this.overallStatus = v; }
        public Double  getInternalMarks() { return internalMarks; }
        public void    setInternalMarks(Double v) { this.internalMarks = v; }
        public Double  getExternalMarks() { return externalMarks; }
        public void    setExternalMarks(Double v) { this.externalMarks = v; }
        public String  getRemarks()       { return remarks; }
        public void    setRemarks(String v) { this.remarks = v; }
        public List<SemesterAdminMark> getSemesterMarks() { return semesterMarks; }
        public void    setSemesterMarks(List<SemesterAdminMark> v) { this.semesterMarks = v; }
    }

    // ── Main getters/setters ──────────────────────────────────────────────────

    public String getId()           { return id; }
    public void   setId(String v)   { this.id = v; }
    public String getStudentId()    { return studentId; }
    public void   setStudentId(String v) { this.studentId = v; }

    public List<SemesterRecord>     getSemesters()       { return semesters; }
    public void setSemesters(List<SemesterRecord> v)     { this.semesters = v; }
    public AttendanceSummary        getAttendance()      { return attendance; }
    public void setAttendance(AttendanceSummary v)       { this.attendance = v; }
    public AssignmentSummary        getAssignments()     { return assignments; }
    public void setAssignments(AssignmentSummary v)      { this.assignments = v; }
    public List<ProjectRecord>      getProjects()        { return projects; }
    public void setProjects(List<ProjectRecord> v)       { this.projects = v; }
    public List<CertificationRecord> getCertifications() { return certifications; }
    public void setCertifications(List<CertificationRecord> v) { this.certifications = v; }
    public List<InternshipRecord>   getInternships()     { return internships; }
    public void setInternships(List<InternshipRecord> v) { this.internships = v; }
    public List<ExamResult>         getExamResults()     { return examResults; }
    public void setExamResults(List<ExamResult> v)       { this.examResults = v; }
    public List<YearCgpa>           getYearCgpas()       { return yearCgpas; }
    public void setYearCgpas(List<YearCgpa> v)           { this.yearCgpas = v; }
    public AdminMarks               getAdminMarks()      { return adminMarks; }
    public void setAdminMarks(AdminMarks v)              { this.adminMarks = v; }
}
