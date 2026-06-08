package com.sms.service;

import com.sms.model.Student;
import com.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Optional<Student> getStudentById(String id) {
        return repository.findById(id);
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public Optional<Student> updateStudent(String id, Student updated) {
        return repository.findById(id).map(existing -> {
            existing.setFullName(updated.getFullName());
            existing.setEmail(updated.getEmail());
            existing.setPhone(updated.getPhone());
            existing.setCourse(updated.getCourse());
            existing.setDepartment(updated.getDepartment());
            existing.setDob(updated.getDob());
            existing.setGpa(updated.getGpa());
            return repository.save(existing);
        });
    }

    public boolean deleteStudent(String id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public List<Student> searchStudents(String query) {
        return repository.searchByNameOrId(query);
    }

    public List<Student> filterStudents(String course, String department) {
        if (course != null && !course.isEmpty() && department != null && !department.isEmpty()) {
            return repository.findByCourseAndDepartment(course, department);
        }
        if (course != null && !course.isEmpty()) return repository.findByCourseIgnoreCase(course);
        if (department != null && !department.isEmpty()) return repository.findByDepartmentIgnoreCase(department);
        return repository.findAll();
    }
}
