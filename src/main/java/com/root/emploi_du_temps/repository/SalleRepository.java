package com.root.emploi_du_temps.repository;

import com.root.emploi_du_temps.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle, Long> {
}