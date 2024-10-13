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

    // Metodo para associar o animal ao adotante (não precisa de injeção de dependência)
    public void associateWithAdopter(AdopterService adopterService) {
        AdopterModel adopter = adopterService.getAdopter(adopterId); // Obtém o adotante pelo ID
        if (adopter != null) {
            adopter.addAdoptedAnimal(this.getId()); // Adiciona o animal à lista de IDs do adotante
        } else {
            System.out.println("Adopter not found with ID: " + adopterId);
        }
    }
}
