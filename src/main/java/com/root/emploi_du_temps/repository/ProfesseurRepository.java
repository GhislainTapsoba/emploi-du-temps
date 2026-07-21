package com.root.emploi_du_temps.repository;

import com.root.emploi_du_temps.model.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
}