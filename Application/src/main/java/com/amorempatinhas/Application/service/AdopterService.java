package com.amorempatinhas.Application.service;

import com.amorempatinhas.Application.dao.AdopterDao;
import com.amorempatinhas.Application.model.AdopterModel;
import com.amorempatinhas.Application.model.AnimalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdopterService {
    private final AdopterDao adopterDao;

    @Autowired
    public AdopterService(AdopterDao adopterDao) {
        this.adopterDao = adopterDao;
    }

    public void createAdopter(AdopterModel adopter) {
        // Adiciona um novo adotante ao sistema
        adopterDao.save(adopter);
    }

    public List<AdopterModel> getAllAdopters() {
        // Retorna todos os adotantes
        return adopterDao.findAll();
    }

    public AdopterModel getAdopter(Integer id) {
        // Recupera um adotante pelo ID
        return adopterDao.findById(id).orElse(null);
    }

    public void deleteAdopter(Integer id) {
        // Remove um adotante do sistema
        if (adopterDao.existsById(id)) {
            adopterDao.deleteById(id);
        } else {
            throw new RuntimeException("Adotante não encontrado");
        }
    }

    public void editAdopter(AdopterModel adopter) {
        // Atualiza as informações de um adotante existente
        if (!adopterDao.existsById(adopter.getId())) {
            throw new RuntimeException("Adotante não encontrado para atualização");
        }
        adopterDao.save(adopter);
    }

    public void addAnimalToAdopter(Integer adopterId, AnimalModel animal) {
        // Adiciona um animal a um adotante
        AdopterModel adopter = getAdopter(adopterId);
        if (adopter == null) {
            throw new RuntimeException("Adotante não encontrado");
        }
        adopter.getAnimals().add(animal);
        animal.setAdopter(adopter);
        adopterDao.save(adopter);
    }

    public void removeAnimalFromAdopter(Integer adopterId, AnimalModel animal) {
        // Remove um animal do adotante
        AdopterModel adopter = getAdopter(adopterId);
        if (adopter == null) {
            throw new RuntimeException("Adotante não encontrado");
        }
        adopter.getAnimals().remove(animal);
        animal.setAdopter(null);
        adopterDao.save(adopter);
    }

    public boolean isAnimalAlreadyAdopted(int animalId) {
        // Verifica se um animal já foi adotado
        return adopterDao.findAll().stream()
                .anyMatch(adopter -> adopter.getAnimals().stream()
                        .anyMatch(animal -> animal.getId() == animalId)); // Usando == para comparação de tipos primitivos
    }

}
