package com.amorempatinhas.Application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String species;
    private int age;
    private String breed;

    @ManyToOne // Relacionamento muitos-para-um
    @JoinColumn(name = "adopter_id") // Nome da coluna que armazena o ID do adotante
    private AdopterModel adopter;

    // Método para associar o animal ao adotante
    public void associateWithAdopter(AdopterModel adopter) {
        this.adopter = adopter; // Associa o animal ao adotante
    }
}
