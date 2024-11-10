package com.amorempatinhas.Application.model;

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
}
