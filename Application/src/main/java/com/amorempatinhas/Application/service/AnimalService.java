package com.amorempatinhas.Application.service;

import com.amorempatinhas.Application.model.AdopterModel;
import com.amorempatinhas.Application.model.AnimalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class AnimalService {
    private List<AnimalModel> animalList;
    private AdopterService adopterService; // Referência ao AdopterService

    @Autowired
    public AnimalService(AdopterService adopterService) {
        this.adopterService = adopterService; // Injeta o AdopterService
        animalList = new ArrayList<>();

        // Adicionando exemplos de animais
        AnimalModel animal1 = new AnimalModel(1, "Luna", "Cachorro", 3, "xaxixa", 2);
        AnimalModel animal2 = new AnimalModel(2, "Tonhao", "Dog", 2, "xaxixa", 2);
        AnimalModel animal3 = new AnimalModel(3, "Lilica", "Cachorro", 1, "SRD", 1);
        animalList.addAll(Arrays.asList(animal1, animal2, animal3));
    }

    public AnimalModel getAnimal(final Integer id) {
        return animalList.stream()
                .filter(animal -> animal.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<AnimalModel> getAnimals() {
        return animalList;
    }

    public boolean isAnimalAlreadyAdopted(Integer animalId) {
        return animalList.stream()
                .anyMatch(animal -> Objects.equals(animal.getId(), animalId) && animal.getAdopterId() != 0);
    }

    public void createAnimal(AnimalModel animal) {
        // Verifica se o animal já está associado a algum adotante
        if (isAnimalAlreadyAdopted(animal.getId())) {
            throw new IllegalArgumentException("Erro: O animal com ID " + animal.getId() + " já está adotado por outro adotante.");
        }

        // Adiciona o animal à lista
        animalList.add(animal);

        // Atualiza a lista de animalIds do adotante
        AdopterModel adopter = adopterService.getAdopter(animal.getAdopterId());
        if (adopter != null) {
            adopter.addAdoptedAnimal(animal.getId());
        }
    }


    public void deleteAnimal(Integer id) {
        // Remove o animal da lista
        AnimalModel animalToRemove = getAnimal(id);
        if (animalToRemove != null) {
            animalList.remove(animalToRemove);

            // Remove o animal da lista de animalIds do adotante
            AdopterModel adopter = adopterService.getAdopter(animalToRemove.getAdopterId());
            if (adopter != null) {
                adopter.getAnimalIds().remove((Integer) id); // Remove o ID do animal
            }
        } else {
            throw new IllegalArgumentException("O animal com ID " + id + " não foi encontrado.");
        }
    }

    public void editAnimal(AnimalModel animal) {
        // Verifica se o animal existe
        AnimalModel existingAnimal = getAnimal(animal.getId());
        if (existingAnimal == null) {
            throw new IllegalArgumentException("O animal com ID " + animal.getId() + " não existe.");
        }

        // Atualiza o animal
        for (int i = 0; i < animalList.size(); i++) {
            if (animalList.get(i).getId() == animal.getId()) {
                animalList.set(i, animal);
                break;
            }
        }

        // Atualiza a lista de animalIds do adotante
        AdopterModel adopter = adopterService.getAdopter(animal.getAdopterId());
        if (adopter != null) {
            // Se o ID do animal não estiver na lista do adotante, adiciona
            if (!adopter.getAnimalIds().contains(animal.getId())) {
                adopter.addAdoptedAnimal(animal.getId());
            }
        } else {
            throw new IllegalArgumentException("Adotante não encontrado para o ID: " + animal.getAdopterId());
        }
    }
}
