package com.root.emploi_du_temps.service;

import com.root.emploi_du_temps.model.Cours;
import com.root.emploi_du_temps.repository.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursService {

    @Autowired
    private CoursRepository coursRepository;

    public List<Cours> findAll() {
        return coursRepository.findAll();
    }

    public Cours findById(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé avec id : " + id));
    }

    public Cours save(Cours cours) {
        return coursRepository.save(cours);
    }

    public void deleteById(Long id) {
        coursRepository.deleteById(id);
    }
}