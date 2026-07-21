package com.root.emploi_du_temps.controller;

import com.root.emploi_du_temps.model.Professeur;
import com.root.emploi_du_temps.service.ProfesseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professeurs")
public class ProfesseurController {

    @Autowired
    private ProfesseurService professeurService;

    @GetMapping
    public List<Professeur> getAll() {
        return professeurService.findAll();
    }

    @GetMapping("/{id}")
    public Professeur getById(@PathVariable Long id) {
        return professeurService.findById(id);
    }

    @PostMapping
    public Professeur create(@RequestBody Professeur professeur) {
        return professeurService.save(professeur);
    }

    @PutMapping("/{id}")
    public Professeur update(@PathVariable Long id, @RequestBody Professeur professeur) {
        professeur.setId(id);
        return professeurService.save(professeur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        professeurService.deleteById(id);
    }
}