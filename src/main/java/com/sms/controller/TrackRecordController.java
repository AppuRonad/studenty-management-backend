package com.sms.controller;

import com.sms.model.TrackRecord;
import com.sms.service.TrackRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/track-records")
@CrossOrigin(origins = "*")
public class TrackRecordController {

    @Autowired
    private TrackRecordService service;

    // GET /api/track-records  — all records (for analytics)
    @GetMapping
    public ResponseEntity<List<TrackRecord>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/track-records/{studentId}
    @GetMapping("/{studentId}")
    public ResponseEntity<?> getByStudentId(@PathVariable String studentId) {
        return service.getByStudentId(studentId.toUpperCase())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/track-records/{studentId}  — full student-side upsert
    @PutMapping("/{studentId}")
    public ResponseEntity<?> upsert(
        @PathVariable String studentId,
        @RequestBody TrackRecord record
    ) {
        TrackRecord saved = service.upsert(studentId.toUpperCase(), record);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/track-records/{studentId}/admin-marks  — admin only
    @PutMapping("/{studentId}/admin-marks")
    public ResponseEntity<?> upsertAdminMarks(
        @PathVariable String studentId,
        @RequestBody TrackRecord.AdminMarks marks
    ) {
        TrackRecord saved = service.upsertAdminMarks(studentId.toUpperCase(), marks);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/track-records/{studentId}/attendance — admin OR member (with addAttendance permission)
    // Partial update: only touches the attendance field, leaves everything else untouched
    @PutMapping("/{studentId}/attendance")
    public ResponseEntity<?> upsertAttendance(
        @PathVariable String studentId,
        @RequestBody TrackRecord.AttendanceSummary attendance
    ) {
        TrackRecord saved = service.upsertAttendance(studentId.toUpperCase(), attendance);
        return ResponseEntity.ok(saved);
    }

    // POST /api/track-records  — create new
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TrackRecord record) {
        TrackRecord saved = service.save(record);
        return ResponseEntity.ok(saved);
    }
}
