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

    @Operation(summary = "Listar todos os animais", description = "Recuperar uma lista de todos os animais registrados no sistema.")
    @GetMapping(produces = {"application/json", "application/xml"})
    public List<AnimalModel> getAnimals() {
        return animalService.getAnimals();
    }

    @Operation(summary = "Criar um novo animal", description = "Adicionar um novo animal ao sistema com os dados fornecidos.")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createAnimal(@RequestBody CreateAnimalDto animal) {
        animalService.createAnimal(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Animal criado com sucesso.");
    }

    @Operation(summary = "Deletar um animal", description = "Remover o animal com o ID fornecido do sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Integer id) {
        // Verificar se o animal com o ID fornecido existe
        AnimalModel animal = animalService.getAnimal(id);
        if (animal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: O animal com ID " + id + " não existe.");
        }

        // Se o animal existir, procede com a exclusão
        try {
            animalService.deleteAnimal(id);
            return ResponseEntity.ok("Animal com ID " + id + " deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar deletar o animal com ID " + id + ": " + e.getMessage());
        }
    }

    @Operation(summary = "Editar um animal", description = "Atualizar os dados de um animal com base nas informações fornecidas utilizando o ID.")
    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editAnimal(@RequestBody PutAnimalDto animal) {
        // Verificar se o animal com o ID fornecido existe
        AnimalModel existingAnimal = animalService.getAnimal(animal.getId());
        if (existingAnimal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: O animal com ID " + animal.getId() + " não existe.");
        }

        // Se o animal existir, procede com a atualização
        try {
            // Atualizar o animal no serviço
            animalService.editAnimal(animal);
            return ResponseEntity.ok("Animal com ID " + animal.getId() + " atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar atualizar o animal com ID " + animal.getId() + ": " + e.getMessage());
        }
    }
}
