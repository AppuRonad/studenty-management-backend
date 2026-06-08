package com.sms.repository;

import com.sms.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

    List<Student> findByCourseIgnoreCase(String course);

    List<Student> findByDepartmentIgnoreCase(String department);

    @Query("{ $or: [ { 'fullName': { $regex: ?0, $options: 'i' } }, { '_id': { $regex: ?0, $options: 'i' } } ] }")
    List<Student> searchByNameOrId(String query);

    List<Student> findByCourseAndDepartment(String course, String department);
}
