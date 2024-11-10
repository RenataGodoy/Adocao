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

    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private AdopterModel adopter;
}
