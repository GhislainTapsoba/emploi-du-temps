package com.root.emploi_du_temps.service;

import com.root.emploi_du_temps.model.Professeur;
import com.root.emploi_du_temps.repository.ProfesseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesseurService {

    @Autowired
    private ProfesseurRepository professeurRepository;

    public List<Professeur> findAll() {
        return professeurRepository.findAll();
    }

    public Professeur findById(Long id) {
        return professeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec id : " + id));
    }

    public Professeur save(Professeur professeur) {
        return professeurRepository.save(professeur);
    }

    public void deleteById(Long id) {
        professeurRepository.deleteById(id);
    }
}