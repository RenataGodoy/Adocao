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
    @GetMapping(produces = {"application/json", "application/xml"})
    public List<AnimalModel> getAnimals() {
        return animalService.getAnimals();
    }

    @Operation(summary = "Criar um novo animal", description = "Cria um novo animal com os dados fornecidos.")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public String createAnimal(@RequestBody CreateAnimalDto animal) {
        animalService.createAnimal(animal);
        return "Animal criado com sucesso";
    }

    @Operation(summary = "Deletar um animal", description = "Deleta o animal com o ID especificado.")
    @DeleteMapping("/{id}")
    public String deleteAnimal(@PathVariable Integer id) {
        // Verificar se o animal com o ID fornecido existe
        AnimalModel animal = animalService.getAnimal(id);
        if (animal == null) {
            return "Erro: O animal com ID " + id + " não existe.";
        }

        // Se o animal existir, procede com a exclusão
        try {
            animalService.deleteAnimal(id);
            return "Animal deletado com sucesso";
        } catch (Exception e) {
            return "Ocorreu um erro ao tentar deletar o animal: " + e.getMessage();
        }
    }


    @Operation(summary = "Editar um animal", description = "Atualiza os dados de um animal com base nas informações fornecidas.")
    @PutMapping(consumes = {"application/json", "application/xml"})
    public String editAnimal(@RequestBody PutAnimalDto animal) {
        // Verificar se o animal com o ID fornecido existe
        AnimalModel existingAnimal = animalService.getAnimal(animal.getId());
        if (existingAnimal == null) {
            return "Erro: O animal com ID " + animal.getId() + " não existe.";
        }

        // Se o animal existir, procede com a atualização
        try {
            // Atualizar o animal no serviço
            animalService.editAnimal(animal);
            return "Animal com ID " + animal.getId() + " atualizado com sucesso!";
        } catch (Exception e) {
            return "Ocorreu um erro ao tentar atualizar o animal: " + e.getMessage();
        }
    }


}
