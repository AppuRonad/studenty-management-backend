package com.sms.controller;

import com.sms.model.TrackRecord;
import com.sms.service.TrackRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/track-records")
@CrossOrigin(origins = "*")
public class TrackRecordController {

    @Autowired
    private TrackRecordService service;

    // GET /api/track-records/{studentId}
    @GetMapping("/{studentId}")
    public ResponseEntity<?> getByStudentId(@PathVariable String studentId) {
        return service.getByStudentId(studentId.toUpperCase())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/track-records/{studentId}  (create or update)
    @PutMapping("/{studentId}")
    public ResponseEntity<?> upsert(
        @PathVariable String studentId,
        @RequestBody TrackRecord record
    ) {
        TrackRecord saved = service.upsert(studentId.toUpperCase(), record);
        return ResponseEntity.ok(saved);
    }

    // POST /api/track-records  (create new)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TrackRecord record) {
        TrackRecord saved = service.save(record);
        return ResponseEntity.ok(saved);
    }
}
