package com.amorempatinhas.Application.api;
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

    @Operation(summary = "Obter Animal por ID", description = "Recuperar as informações de um animal específico")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<AnimalModel> getAnimal(@PathVariable Integer id) {
        AnimalModel animal = animalService.getAnimal(id);
        if (animal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(animal);
        }
        return ResponseEntity.status(HttpStatus.OK).body(animal);
    }

    @Operation(summary = "Obter Todos os Animais", description = "Recuperar uma lista de todos os animais")
    @GetMapping
    public List<AnimalModel> getAnimals() {
        return animalService.getAnimals();
    }

    @Operation(summary = "Criar um novo Animal", description = "Adicionar um novo animal ao sistema")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createAnimal(@RequestBody AnimalModel animal) {
        try {
            animalService.createAnimal(animal);
            return ResponseEntity.status(HttpStatus.CREATED).body("Animal criado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro na criação do animal: " + e);
        }
    }

    @Operation(summary = "Deletar um Animal", description = "Remover um animal do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Integer id) {
        if (animalService.getAnimal(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal não encontrado para DELETE");
        }
        animalService.deleteAnimal(id);
        return ResponseEntity.status(HttpStatus.OK).body("Animal deletado com sucesso");
    }

    @Operation(summary = "Atualizar um Animal", description = "Modificar as informações de um animal existente")
    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editAnimal(@RequestBody AnimalModel animal) {
        try {
            animalService.editAnimal(animal);
            return ResponseEntity.status(HttpStatus.OK).body("Animal atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro na atualização do animal: " + e);
        }
    }
}
