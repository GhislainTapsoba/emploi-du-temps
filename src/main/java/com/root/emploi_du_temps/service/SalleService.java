package com.root.emploi_du_temps.service;

import com.root.emploi_du_temps.model.Salle;
import com.root.emploi_du_temps.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;

    public List<Salle> findAll() {
        return salleRepository.findAll();
    }

    public Salle findById(Long id) {
        return salleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salle non trouvée avec id : " + id));
    }

    public Salle save(Salle salle) {
        return salleRepository.save(salle);
    }

    public void deleteById(Long id) {
        salleRepository.deleteById(id);
    }
}