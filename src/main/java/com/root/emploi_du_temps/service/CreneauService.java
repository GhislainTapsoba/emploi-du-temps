package com.root.emploi_du_temps.service;

import com.root.emploi_du_temps.model.Creneau;
import com.root.emploi_du_temps.repository.CreneauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class CreneauService {

    @Autowired
    private CreneauRepository creneauRepository;

    public List<Creneau> findAll() {
        return creneauRepository.findAll();
    }

    public Creneau findById(Long id) {
        return creneauRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Créneau non trouvé avec id : " + id));
    }

    public Creneau save(Creneau creneau) {
        verifierConflitSalle(creneau);
        verifierConflitProfesseur(creneau);
        return creneauRepository.save(creneau);
    }

    public void deleteById(Long id) {
        creneauRepository.deleteById(id);
    }

    // Vérifie qu'aucun autre créneau n'utilise la même salle au même moment
    private void verifierConflitSalle(Creneau creneau) {
        List<Creneau> creneauxExistants = creneauRepository
                .findBySalleIdAndJour(creneau.getSalle().getId(), creneau.getJour());

        for (Creneau existant : creneauxExistants) {
            if (existant.getId() != null && existant.getId().equals(creneau.getId())) {
                continue; // on ignore le créneau qu'on est en train de modifier
            }
            if (seChevauchent(creneau, existant)) {
                throw new RuntimeException(
                    "Conflit : la salle " + creneau.getSalle().getNom() +
                    " est déjà occupée le " + creneau.getJour() +
                    " à " + existant.getHeureDebut()
                );
            }
        }
    }

    // Vérifie que le professeur n'a pas déjà un autre cours au même moment
    private void verifierConflitProfesseur(Creneau creneau) {
        Long professeurId = creneau.getCours().getProfesseur().getId();
        List<Creneau> creneauxExistants = creneauRepository
                .findByCours_Professeur_IdAndJour(professeurId, creneau.getJour());

        for (Creneau existant : creneauxExistants) {
            if (existant.getId() != null && existant.getId().equals(creneau.getId())) {
                continue;
            }
            if (seChevauchent(creneau, existant)) {
                throw new RuntimeException(
                    "Conflit : le professeur " + creneau.getCours().getProfesseur().getNom() +
                    " a déjà un cours le " + creneau.getJour() +
                    " à " + existant.getHeureDebut()
                );
            }
        }
    }

    // Vérifie si deux créneaux se chevauchent dans le temps
    private boolean seChevauchent(Creneau nouveau, Creneau existant) {
        LocalTime debutNouveau = nouveau.getHeureDebut();
        LocalTime finNouveau = debutNouveau.plusMinutes(nouveau.getCours().getDureeMinutes());

        LocalTime debutExistant = existant.getHeureDebut();
        LocalTime finExistant = debutExistant.plusMinutes(existant.getCours().getDureeMinutes());

        return debutNouveau.isBefore(finExistant) && debutExistant.isBefore(finNouveau);
    }
}