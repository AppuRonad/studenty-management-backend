package com.sms.controller;

import com.sms.model.Competition;
import com.sms.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/competitions")
@CrossOrigin(origins = "*")
public class CompetitionController {

    @Autowired
    private CompetitionService service;

    // GET /api/competitions/{studentId}
    @GetMapping("/{studentId}")
    public ResponseEntity<List<Competition>> getByStudent(
        @PathVariable String studentId,
        @RequestParam(required = false) String category
    ) {
        String id = studentId.toUpperCase();
        if (category != null && !category.isEmpty()) {
            return ResponseEntity.ok(service.getByStudentIdAndCategory(id, category));
        }
        return ResponseEntity.ok(service.getByStudentId(id));
    }

    // POST /api/competitions  (add one competition)
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Competition competition) {
        competition.setStudentId(competition.getStudentId().toUpperCase());
        return ResponseEntity.ok(service.add(competition));
    }

    // POST /api/competitions/bulk  (seed multiple competitions)
    @PostMapping("/bulk")
    public ResponseEntity<?> addBulk(@RequestBody List<Competition> competitions) {
        competitions.forEach(c -> c.setStudentId(c.getStudentId().toUpperCase()));
        service.saveAll(competitions);
        return ResponseEntity.ok(Map.of("message", "Saved " + competitions.size() + " competitions"));
    }

    // PUT /api/competitions/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable String id,
        @RequestBody Competition competition
    ) {
        return service.update(id, competition)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/competitions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (service.delete(id)) {
            return ResponseEntity.ok(Map.of("message", "Deleted"));
        }
        return ResponseEntity.notFound().build();
    }
}
