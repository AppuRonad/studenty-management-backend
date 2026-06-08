package com.sms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "competitions")
public class Competition {

    @Id
    private String id;

    @NotBlank
    private String studentId;

    @NotBlank
    private String title;

    private String category;
    private String position;
    private String organizer;
    private String date;
    private String prize;
    private String team;
    private String description;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getPrize() { return prize; }
    public void setPrize(String prize) { this.prize = prize; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
