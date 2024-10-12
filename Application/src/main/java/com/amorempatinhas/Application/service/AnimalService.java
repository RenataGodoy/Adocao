package com.amorempatinhas.Application.service;

import com.amorempatinhas.Application.model.AnimalModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AnimalService {
    private List<AnimalModel> animalList;

    public AnimalService() {
        animalList = new ArrayList<>();

        // Adicionando exemplos de animais
        AnimalModel animal1 = new AnimalModel(1, "Rex", "Cachorro", 3, "Labrador", 1);
        AnimalModel animal2 = new AnimalModel(2, "Miau", "Gato", 2, "Siamês", 2);
        AnimalModel animal3 = new AnimalModel(2, "Lilica", "Cachorro", 1, "SRD", 1);
        animalList.addAll(Arrays.asList(animal1, animal2));
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
        animalList.add(animal);
    }

    public void deleteAnimal(Integer id) {
        animalList.removeIf(animal -> animal.getId() == id);
    }

    public void editAnimal(AnimalModel animal) {
        for (int i = 0; i < animalList.size(); i++) {
            if (animalList.get(i).getId() == animal.getId()) {
                animalList.set(i, animal);
                break;
            }
        }
    }
}
