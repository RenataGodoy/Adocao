package com.amorempatinhas.Application.api;
import com.amorempatinhas.Application.model.AdopterModel;
import com.amorempatinhas.Application.service.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adotante com ID " + id + " não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(adopter);
    }


    @Operation(summary = "Obter Todos os Adotantes", description = "Recuperar uma lista de todos os adotantes")
    @GetMapping
    public List<AdopterModel> getAdopters() {
        return adopterService.getAdopters();
    }

    @Operation(summary = "Criar um novo Adotante", description = "Adicionar um novo adotante ao sistema")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createAdopter(@RequestBody AdopterModel adopter) {
        try {
            // Verifica se já existe um adotante com o mesmo ID
            AdopterModel existingAdopter = adopterService.getAdopter(adopter.getId());
            if (existingAdopter != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Já existe um adotante com o ID " + adopter.getId());
            }

            // Cria o adotante
            adopterService.createAdopter(adopter);

            // Adiciona animais ao adotante recém-criado, se houver
            if (!adopter.getAnimalIds().isEmpty()) {
                for (Integer animalId : adopter.getAnimalIds()) {
                    adopterService.addAnimalToAdopter(adopter.getId(), animalId);
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Adotante criado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro na criação do adotante: " + e.getMessage());
        }
    }



    @Operation(summary = "Deletar um Adotante", description = "Remover um adotante do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdopter(@PathVariable Integer id) {
        AdopterModel adopter = adopterService.getAdopter(id);
        if (adopter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adotante não encontrado para excluir");
        }

        try {
            adopterService.deleteAdopter(id);
            return ResponseEntity.status(HttpStatus.OK).body("Adotante deletado com sucesso");
        } catch (Exception e) {
            // Captura de erros inesperados durante a exclusão
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar excluir o adotante: " + e.getMessage());
        }
    }


    @Operation(summary = "Atualizar um Adotante", description = "Modificar as informações de um adotante existente")
    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editAdopter(@RequestBody AdopterModel adopter) {
        try {
            adopterService.editAdopter(adopter);
            return ResponseEntity.status(HttpStatus.OK).body("Adotante atualizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro na atualização do adotante: " + e);
        }
    }

}
