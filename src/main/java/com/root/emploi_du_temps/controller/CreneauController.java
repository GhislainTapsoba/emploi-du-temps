package com.root.emploi_du_temps.controller;

import com.root.emploi_du_temps.model.Creneau;
import com.root.emploi_du_temps.service.CreneauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creneaux")
public class CreneauController {

    @Autowired
    private CreneauService creneauService;

    @GetMapping
    public List<Creneau> getAll() {
        return creneauService.findAll();
    }

    @GetMapping("/{id}")
    public Creneau getById(@PathVariable Long id) {
        return creneauService.findById(id);
    }

    @PostMapping
    public Creneau create(@RequestBody Creneau creneau) {
        return creneauService.save(creneau);
    }

    @PutMapping("/{id}")
    public Creneau update(@PathVariable Long id, @RequestBody Creneau creneau) {
        creneau.setId(id);
        return creneauService.save(creneau);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        creneauService.deleteById(id);
    }
}