package com.sms.service;

import com.sms.model.Competition;
import com.sms.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository repository;

    public List<Competition> getByStudentId(String studentId) {
        return repository.findByStudentId(studentId);
    }

    public List<Competition> getByStudentIdAndCategory(String studentId, String category) {
        return repository.findByStudentIdAndCategory(studentId, category);
    }

    public Competition add(Competition competition) {
        return repository.save(competition);
    }

    public Optional<Competition> update(String id, Competition updated) {
        return repository.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setCategory(updated.getCategory());
            existing.setPosition(updated.getPosition());
            existing.setOrganizer(updated.getOrganizer());
            existing.setDate(updated.getDate());
            existing.setPrize(updated.getPrize());
            existing.setTeam(updated.getTeam());
            existing.setDescription(updated.getDescription());
            return repository.save(existing);
        });
    }

    public boolean delete(String id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public void saveAll(List<Competition> competitions) {
        repository.saveAll(competitions);
    }

    public boolean hasCompetitions(String studentId) {
        return !repository.findByStudentId(studentId).isEmpty();
    }
}
