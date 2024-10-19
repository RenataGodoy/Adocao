package com.amorempatinhas.Application.model;

import com.amorempatinhas.Application.service.AdopterService;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalModel {

    private int id;
    private String name;
    private String species;
    private int age;
    private String breed;
    private int adopterId;

    // MEtodo para associar o animal ao adotante
    public void associateWithAdopter(AdopterService adopterService) {
        AdopterModel adopter = adopterService.getAdopter(adopterId); // Obtém o adotante pelo ID
        if (adopter != null) {
            // Verifica se o adotante já possui este animal
            if (!adopter.hasAnimal(this.id)) {
                adopter.addAdoptedAnimal(this.id); // Adiciona o animal à lista de IDs do adotante
            } else {
                throw new IllegalArgumentException("Este animal já está associado ao adotante com ID: " + adopterId);
            }
        } else {
            throw new IllegalArgumentException("Adotante não encontrado com ID: " + adopterId);
        }
    }
}
