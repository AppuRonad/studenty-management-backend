package com.sms.controller;

import com.sms.model.Student;
import com.sms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping
    public ResponseEntity<List<Student>> getAll(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String course,
        @RequestParam(required = false) String department
    ) {
        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(service.searchStudents(search));
        }
        if (course != null || department != null) {
            return ResponseEntity.ok(service.filterStudents(course, department));
        }
        return ResponseEntity.ok(service.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return service.getStudentById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Student student) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createStudent(student));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody Student student) {
        return service.updateStudent(id, student)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (service.deleteStudent(id)) {
            return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        List<Student> all = service.getAllStudents();
        double avgGpa = all.stream()
            .filter(s -> s.getGpa() != null)
            .mapToDouble(Student::getGpa)
            .average().orElse(0.0);
        return ResponseEntity.ok(Map.of(
            "total", all.size(),
            "averageGpa", Math.round(avgGpa * 100.0) / 100.0
        ));
    }
}
