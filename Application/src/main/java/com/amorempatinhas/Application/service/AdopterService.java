package com.amorempatinhas.Application.service;
import com.amorempatinhas.Application.model.AdopterModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdopterService {
    private List<AdopterModel> adopterList;

    public AdopterService() {
        adopterList = new ArrayList<>();

        // Adicionando exemplos de adotantes
        AdopterModel adopter1 = new AdopterModel(1, "Renata", "123.456.789-00", "renata@gmail.com", "9999-9999", new ArrayList<>());
        AdopterModel adopter2 = new AdopterModel(2, "Maria", "987.654.321-00", "maria@gmail.com", "8888-8888", new ArrayList<>());

        adopterList.addAll(Arrays.asList(adopter1, adopter2));
    }

    public AdopterModel getAdopter(final Integer id) {
        return adopterList.stream()
                .filter(adopter -> adopter.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<AdopterModel> getAdopters() {
        return adopterList;
    }

    public void createAdopter(AdopterModel adopter) {
        adopterList.add(adopter);
    }

    public void deleteAdopter(Integer id) {
        adopterList.removeIf(adopter -> adopter.getId() == id);
    }

    public void editAdopter(AdopterModel adopter) {
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


}
