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

    // Upsert — create if not exists, update if exists
    public TrackRecord upsert(String studentId, TrackRecord record) {
        return repository.findByStudentId(studentId).map(existing -> {
            existing.setSemesters(record.getSemesters());
            existing.setAttendance(record.getAttendance());
            existing.setAssignments(record.getAssignments());
            existing.setProjects(record.getProjects());
            existing.setCertifications(record.getCertifications());
            return repository.save(existing);
        }).orElseGet(() -> {
            record.setStudentId(studentId);
            return repository.save(record);
        });
    }

    public boolean existsForStudent(String studentId) {
        return repository.existsByStudentId(studentId);
    }
}
