package com.root.emploi_du_temps.repository;

import com.root.emploi_du_temps.model.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours, Long> {
}