package com.sms.service;

import com.sms.model.TrackRecord;
import com.sms.repository.TrackRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TrackRecordService {

    @Autowired
    private TrackRecordRepository repository;

    public Optional<TrackRecord> getByStudentId(String studentId) {
        return repository.findByStudentId(studentId);
    }

    public TrackRecord save(TrackRecord record) {
        return repository.save(record);
    }

    public java.util.List<TrackRecord> getAll() {
        return repository.findAll();
    }

    // Full upsert — create or replace all student-editable fields
    public TrackRecord upsert(String studentId, TrackRecord record) {
        return repository.findByStudentId(studentId).map(existing -> {
            existing.setSemesters(record.getSemesters());
            existing.setAttendance(record.getAttendance());
            existing.setAssignments(record.getAssignments());
            existing.setProjects(record.getProjects());
            existing.setCertifications(record.getCertifications());
            existing.setInternships(record.getInternships());
            existing.setExamResults(record.getExamResults());
            existing.setYearCgpas(record.getYearCgpas());
            // Never overwrite adminMarks from student-side upsert
            return repository.save(existing);
        }).orElseGet(() -> {
            record.setStudentId(studentId);
            return repository.save(record);
        });
    }

    // Admin-only: update marks / grade / pass-fail
    public TrackRecord upsertAdminMarks(String studentId, TrackRecord.AdminMarks marks) {
        return repository.findByStudentId(studentId).map(existing -> {
            existing.setAdminMarks(marks);
            return repository.save(existing);
        }).orElseGet(() -> {
            TrackRecord rec = new TrackRecord();
            rec.setStudentId(studentId);
            rec.setAdminMarks(marks);
            return repository.save(rec);
        });
    }

    // Admin or Member (with addAttendance permission): partial update of attendance only
    public TrackRecord upsertAttendance(String studentId, TrackRecord.AttendanceSummary attendance) {
        return repository.findByStudentId(studentId).map(existing -> {
            existing.setAttendance(attendance);
            return repository.save(existing);
        }).orElseGet(() -> {
            TrackRecord rec = new TrackRecord();
            rec.setStudentId(studentId);
            rec.setAttendance(attendance);
            return repository.save(rec);
        });
    }

    public boolean existsForStudent(String studentId) {
        return repository.existsByStudentId(studentId);
    }
}
