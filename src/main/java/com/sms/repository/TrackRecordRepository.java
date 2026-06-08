package com.sms.repository;

import com.sms.model.TrackRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface TrackRecordRepository extends MongoRepository<TrackRecord, String> {
    Optional<TrackRecord> findByStudentId(String studentId);
    boolean existsByStudentId(String studentId);
    void deleteByStudentId(String studentId);
}
