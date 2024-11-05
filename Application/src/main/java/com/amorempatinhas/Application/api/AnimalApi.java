package com.amorempatinhas.Application.api;

import com.amorempatinhas.Application.model.AnimalModel;
import com.amorempatinhas.Application.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalApi {
    private final AnimalService animalService;

    @Autowired
    public AnimalApi(final AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnimal(@PathVariable Integer id) {
        AnimalModel animal = animalService.getAnimal(id);
        if (animal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O animal com ID " + id + " não existe.");
        }
        return ResponseEntity.ok(animal);
    }

    @GetMapping
    public List<AnimalModel> getAnimals() {
        return animalService.getAnimals();
    }

    @PostMapping
    public ResponseEntity<String> createAnimal(@RequestBody AnimalModel animal) {
        animalService.createAnimal(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Animal criado com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Integer id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok("Animal deletado com sucesso");
    }

    @PutMapping
    public ResponseEntity<String> editAnimal(@RequestBody AnimalModel animal) {
        animalService.editAnimal(animal);
        return ResponseEntity.ok("Animal atualizado com sucesso!");
    }
}
