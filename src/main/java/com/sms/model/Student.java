package com.sms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Document(collection = "students")
public class Student {

    @Id
    private String id;

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100)
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "Course is required")
    private String course;

    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "Date of birth is required")
    private LocalDate dob;

    private Double gpa;

    private LocalDate enrolledDate = LocalDate.now();

    private int avatar;

    // ── New academic fields ──────────────────────────────────────────────────
    private Integer yearOfStudy;       // 1 / 2 / 3 / 4
    private Integer courseDuration;    // total years e.g. 4 for B.Tech, 2 for MBA
    private String  academicYear;      // e.g. "2023-24"
    private String  admissionNumber;   // optional

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }

    public LocalDate getEnrolledDate() { return enrolledDate; }
    public void setEnrolledDate(LocalDate enrolledDate) { this.enrolledDate = enrolledDate; }

    public int getAvatar() { return avatar; }
    public void setAvatar(int avatar) { this.avatar = avatar; }

    public Integer getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(Integer yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public Integer getCourseDuration() { return courseDuration; }
    public void setCourseDuration(Integer courseDuration) { this.courseDuration = courseDuration; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getAdmissionNumber() { return admissionNumber; }
    public void setAdmissionNumber(String admissionNumber) { this.admissionNumber = admissionNumber; }
}
