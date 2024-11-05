package com.amorempatinhas.Application.service;

import com.amorempatinhas.Application.Dao.AdopterDao;
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

    public void addAnimalToAdopter(int adopterId, AnimalModel animal) {
        AdopterModel adopter = getAdopter(adopterId);
        if (adopter != null) {
            adopter.addAdoptedAnimal(animal);
            adopterDao.save(adopter); // Persiste a atualização
        } else {
            throw new IllegalArgumentException("Adotante com ID " + adopterId + " não encontrado.");
        }
    }

    public AdopterModel getAdopter(final Integer id) {
        return adopterDao.findById(id).orElse(null); // Usando Optional
    }

    public List<AdopterModel> getAdopters() {
        return adopterDao.findAll();
    }

    public void createAdopter(AdopterModel adopter) {
        adopterDao.save(adopter);
    }

    public void deleteAdopter(Integer id) {
        adopterDao.deleteById(id);
    }

    public void editAdopter(AdopterModel adopter) {
        adopterDao.save(adopter); // Atualiza o adotante existente
    }

    public boolean isAnimalAlreadyAdopted(int animalId) {
        return adopterDao.findAll().stream()
                .anyMatch(adopter -> adopter.getAnimalIds().contains(animalId));
    }
}
