package com.amorempatinhas.Application.api;

import com.amorempatinhas.Application.dto.CreateAdopterDto;
import com.amorempatinhas.Application.dto.PutAdopterDto;
import com.amorempatinhas.Application.model.AdopterModel;
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
public class AdopterApi {
    private final AdopterService adopterService;

    @Autowired
    public AdopterApi(final AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @Operation(summary = "Obter Adotante por ID", description = "Recuperar as informações de um adotante específico")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAdopter(@PathVariable Integer id) {
        AdopterModel adopter = adopterService.getAdopter(id);
        if (adopter == null) {
            // Resposta mais detalhada em caso de erro, indicando que o ID não foi encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adotante com ID " + id + " não encontrado.");
        }
        return ResponseEntity.ok(adopter);
    }


    @Operation(summary = "Obter Todos os Adotantes", description = "Recuperar uma lista de todos os adotantes registrados.")
    @GetMapping(produces = {"application/json", "application/xml"})
    public List<AdopterModel> getAdopters() {
        return adopterService.getAllAdopters();
    }

    @Operation(summary = "Criar um novo Adotante", description = "Adicionar um novo adotante ao sistema.")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createAdopter(@RequestBody @Validated CreateAdopterDto adopter) {
        try {
            // Verifica se já existe um adotante com o mesmo ID
            if (adopterService.getAdopter(adopter.getId()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Já existe um adotante com o ID " + adopter.getId());
            }

            adopterService.createAdopter(adopter);
            return ResponseEntity.status(HttpStatus.CREATED).body("Adotante com ID " + adopter.getId() + " criado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao criar o adotante com ID " + adopter.getId() + ": " + e.getMessage());
        }
    }

    @Operation(summary = "Deletar um Adotante", description = "Remover um adotante do sistema utilizando o ID fornecido.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdopter(@PathVariable Integer id) {
        try {
            AdopterModel adopter = adopterService.getAdopter(id);
            if (adopter == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adotante com ID " + id + " não encontrado para exclusão.");
            }

            adopterService.deleteAdopter(id);
            return ResponseEntity.ok("Adotante com ID " + id + " deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar excluir o adotante com ID " + id + ": " + e.getMessage());
        }
    }

    @Operation(summary = "Atualizar um Adotante", description = "Modificar as informações de um adotante existente utilizando o ID fornecido.")
    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editAdopter(@RequestBody PutAdopterDto adopter) {
        try {
            if (adopterService.getAdopter(adopter.getId()) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adotante com ID " + adopter.getId() + " não encontrado para atualização.");
            }

            adopterService.editAdopter(adopter);
            return ResponseEntity.ok("Adotante com ID " + adopter.getId() + " atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o adotante com ID " + adopter.getId() + ": " + e.getMessage());
        }
    }
}
