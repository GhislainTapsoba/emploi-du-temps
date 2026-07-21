package com.root.emploi_du_temps.controller;

import com.root.emploi_du_temps.model.Cours;
import com.root.emploi_du_temps.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    @Autowired
    private CoursService coursService;

    @GetMapping
    public List<Cours> getAll() {
        return coursService.findAll();
    }

    @GetMapping("/{id}")
    public Cours getById(@PathVariable Long id) {
        return coursService.findById(id);
    }

    @PostMapping
    public Cours create(@RequestBody Cours cours) {
        return coursService.save(cours);
    }

    @PutMapping("/{id}")
    public Cours update(@PathVariable Long id, @RequestBody Cours cours) {
        cours.setId(id);
        return coursService.save(cours);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        coursService.deleteById(id);
    }
}