package com.amorempatinhas.Application.service;

import com.amorempatinhas.Application.model.AdopterModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdopterService {
    private static List<AdopterModel> adopterList;

    public AdopterService() {
        adopterList = new ArrayList<>();

        // Adicionando exemplos de adotantes
        AdopterModel adopter1 = new AdopterModel(1, "Renata", "123.456.789-00", "renata@gmail.com", "9999-9999", new ArrayList<>(Arrays.asList(3)));
        AdopterModel adopter2 = new AdopterModel(2, "Maria", "987.654.321-00", "maria@gmail.com", "8888-8888", new ArrayList<>(Arrays.asList(1, 2)));

        adopterList.addAll(Arrays.asList(adopter1, adopter2));
    }

    public void addAnimalToAdopter(int adopterId, int animalId) {
        AdopterModel adopter = getAdopter(adopterId);
        if (adopter != null) {
            if (!adopter.getAnimalIds().contains(animalId)) {
                adopter.addAdoptedAnimal(animalId);
            } else {
                throw new IllegalArgumentException("Erro: O animal com ID " + animalId + " já está adotado por este adotante.");
            }
        } else {
            throw new IllegalArgumentException("Adotante com ID " + adopterId + " não encontrado.");
        }
    }

    public static AdopterModel getAdopter(final Integer id) {
        return adopterList.stream()
                .filter(adopter -> adopter.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<AdopterModel> getAdopters() {
        return adopterList;
    }

    public void createAdopter(AdopterModel adopter) {
        // Verifica se já existe um adotante com o mesmo ID
        if (getAdopter(adopter.getId()) != null) {
            throw new IllegalArgumentException("Erro: Já existe um adotante com o ID " + adopter.getId());
        }

        // Adiciona o adotante à lista
        adopterList.add(adopter);
    }

    public static void deleteAdopter(Integer id) {
        adopterList.removeIf(adopter -> adopter.getId() == id);
    }

    public static void editAdopter(AdopterModel adopter) {
        boolean found = false; // Flag para verificar se o adotante foi encontrado
        for (int i = 0; i < adopterList.size(); i++) {
            if (adopterList.get(i).getId() == adopter.getId()) {
                adopterList.set(i, adopter);
                found = true; // Atualiza a flag se o adotante for encontrado
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Adotante com ID " + adopter.getId() + " não encontrado.");
        }
    }

    public boolean isAnimalAlreadyAdopted(Integer animalId) {
        // Verifica se algum adotante possui o animalId na lista de IDs de animais adotados
        return adopterList.stream()
                .anyMatch(adopter -> adopter.getAnimalIds().contains(animalId));
    }

}
