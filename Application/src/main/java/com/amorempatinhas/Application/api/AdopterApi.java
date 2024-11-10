package com.amorempatinhas.Application.api;

import com.amorempatinhas.Application.model.AdopterModel;
import com.amorempatinhas.Application.model.AnimalModel;
import com.amorempatinhas.Application.service.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/adopters")
@Validated
public class AdopterApi {
    private final AdopterService adopterService;

    @Autowired
    public AdopterApi(final AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @Operation(summary = "Obter Adotante por ID", description = "Recuperar as informações de um adotante específico")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<AdopterModel> getAdopter(@PathVariable Integer id) {
        AdopterModel adopter = adopterService.getAdopter(id);
        if (adopter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(adopter);
    }

    @Operation(summary = "Obter Todos os Adotantes", description = "Recuperar uma lista de todos os adotantes")
    @GetMapping
    public List<AdopterModel> getAdopters() {
        return adopterService.getAllAdopters();
    }

    @Operation(summary = "Criar um novo Adotante", description = "Adicionar um novo adotante ao sistema")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createAdopter(@RequestBody @Validated AdopterModel adopter) {
        try {
            // Verifica se já existe um adotante com o mesmo ID
            if (adopterService.getAdopter(adopter.getId()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Já existe um adotante com o ID " + adopter.getId());
            }

            // Verifica se os animais já estão adotados
            for (AnimalModel animal : adopter.getAnimals()) {
                if (adopterService.isAnimalAlreadyAdopted(animal.getId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Erro: O animal com ID " + animal.getId() + " já está adotado por outro adotante.");
                }
            }

            adopterService.createAdopter(adopter);
            return ResponseEntity.status(HttpStatus.CREATED).body("Adotante criado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro na criação do adotante: " + e.getMessage());
        }
    }

    @Operation(summary = "Deletar um Adotante", description = "Remover um adotante do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdopter(@PathVariable Integer id) {
        try {
            AdopterModel adopter = adopterService.getAdopter(id);
            if (adopter == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adotante não encontrado para excluir");
            }

            adopterService.deleteAdopter(id);
            return ResponseEntity.status(HttpStatus.OK).body("Adotante deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar excluir o adotante: " + e.getMessage());
        }
    }

    @Operation(summary = "Atualizar um Adotante", description = "Modificar as informações de um adotante existente")
    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editAdopter(@RequestBody @Validated AdopterModel adopter) {
        try {
            if (adopterService.getAdopter(adopter.getId()) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adotante com ID " + adopter.getId() + " não encontrado.");
            }

            adopterService.editAdopter(adopter);
            return ResponseEntity.status(HttpStatus.OK).body("Adotante atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro na atualização do adotante: " + e.getMessage());
        }
    }
}
