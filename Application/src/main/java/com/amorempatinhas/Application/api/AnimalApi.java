package com.amorempatinhas.Application.api;

import com.amorempatinhas.Application.dto.CreateAnimalDto;
import com.amorempatinhas.Application.dto.PutAnimalDto;
import com.amorempatinhas.Application.model.AnimalModel;
import com.amorempatinhas.Application.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Buscar um animal por ID", description = "Retorna os detalhes do animal com base no ID fornecido.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnimal(@PathVariable Integer id) {
        AnimalModel animal = animalService.getAnimal(id);
        if (animal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O animal com ID " + id + " não existe.");
        }
        return ResponseEntity.ok(animal);
    }

    @Operation(summary = "Listar todos os animais", description = "Retorna uma lista de todos os animais registrados.")
    @GetMapping
    public List<AnimalModel> getAnimals() {
        return animalService.getAnimals();
    }

    @Operation(summary = "Criar um novo animal", description = "Cria um novo animal com os dados fornecidos.")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createAnimal(@RequestBody CreateAnimalDto animal) {
        animalService.createAnimal(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Animal criado com sucesso");
    }

    @Operation(summary = "Deletar um animal", description = "Deleta o animal com o ID especificado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Integer id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok("Animal deletado com sucesso");
    }

    @Operation(summary = "Editar um animal", description = "Atualiza os dados de um animal com base nas informações fornecidas.")
    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editAnimal(@RequestBody PutAnimalDto animal) {
        animalService.editAnimal(animal);
        return ResponseEntity.ok("Animal atualizado com sucesso!");
    }
}
