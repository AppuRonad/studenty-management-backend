package com.sms.repository;

import com.sms.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Member> findByDepartment(String department);
    List<Member> findByStatus(String status);
}
