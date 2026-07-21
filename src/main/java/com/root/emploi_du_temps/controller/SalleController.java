package com.root.emploi_du_temps.controller;

import com.root.emploi_du_temps.model.Salle;
import com.root.emploi_du_temps.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
public class SalleController {

    @Autowired
    private SalleService salleService;

    @GetMapping
    public List<Salle> getAll() {
        return salleService.findAll();
    }

    @GetMapping("/{id}")
    public Salle getById(@PathVariable Long id) {
        return salleService.findById(id);
    }

    @PostMapping
    public Salle create(@RequestBody Salle salle) {
        return salleService.save(salle);
    }

    @PutMapping("/{id}")
    public Salle update(@PathVariable Long id, @RequestBody Salle salle) {
        salle.setId(id);
        return salleService.save(salle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        salleService.deleteById(id);
    }
}