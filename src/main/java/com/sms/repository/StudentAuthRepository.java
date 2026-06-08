package com.sms.repository;

import com.sms.model.StudentAuth;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentAuthRepository extends MongoRepository<StudentAuth, String> {
    boolean existsByStudentId(String studentId);
}
