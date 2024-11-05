package com.amorempatinhas.Application.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "adopters")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("AdopterModel")
public class AdopterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String cpf;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalModel> animals = new ArrayList<>();

    public void addAdoptedAnimal(AnimalModel animal) {
        if (!animals.contains(animal)) {
            animals.add(animal);
            animal.setAdopter(this); // Configura a referência do lado do animal
        } else {
            throw new IllegalArgumentException("O animal já está associado a este adotante.");
        }
    }

    public boolean hasAnimal(AnimalModel animal) {
        return animals.contains(animal);
    }

    public List<Integer> getAnimalIds() {
        List<Integer> animalIds = new ArrayList<>();
        for (AnimalModel animal : animals) {
            animalIds.add(animal.getId());
        }
        return animalIds;
    }
}
