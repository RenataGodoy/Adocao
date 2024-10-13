package com.amorempatinhas.Application.service;

import com.amorempatinhas.Application.model.AdopterModel;
import com.amorempatinhas.Application.model.AnimalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AnimalService {
    private List<AnimalModel> animalList;
    private AdopterService adopterService; // Referência ao AdopterService

    @Autowired
    public AnimalService(AdopterService adopterService) {
        this.adopterService = adopterService; // Injeta o AdopterService
        animalList = new ArrayList<>();

        // Adicionando exemplos de animais
        AnimalModel animal1 = new AnimalModel(1, "Rex", "Cachorro", 3, "Labrador", 1);
        AnimalModel animal2 = new AnimalModel(2, "Miau", "Gato", 2, "Siamês", 2);
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

    public void createAnimal(AnimalModel animal) {
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
        }
    }

    public void editAnimal(AnimalModel animal) {
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
        }
    }
}
