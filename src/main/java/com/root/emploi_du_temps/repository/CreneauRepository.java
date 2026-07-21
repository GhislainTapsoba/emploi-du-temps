package com.root.emploi_du_temps.repository;

import com.root.emploi_du_temps.model.Creneau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface CreneauRepository extends JpaRepository<Creneau, Long> {

    // Créneaux existants dans une salle donnée, un jour donné
    List<Creneau> findBySalleIdAndJour(Long salleId, DayOfWeek jour);

    // Créneaux existants pour un prof donné (via son cours), un jour donné
    List<Creneau> findByCours_Professeur_IdAndJour(Long professeurId, DayOfWeek jour);
}