package com.sms.repository;

import com.sms.model.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CompetitionRepository extends MongoRepository<Competition, String> {
    List<Competition> findByStudentId(String studentId);
    List<Competition> findByStudentIdAndCategory(String studentId, String category);
    void deleteByStudentId(String studentId);
}
