package com.amorempatinhas.Application.service;

import com.amorempatinhas.Application.Dao.AnimalDao;
import com.amorempatinhas.Application.model.AnimalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {
    private final AnimalDao animalDao;

    @Autowired
    public AnimalService(AnimalDao animalDao) {
        this.animalDao = animalDao;
    }

    public AnimalModel getAnimal(final Integer id) {
        return animalDao.findById(id).orElse(null);
    }

    public List<AnimalModel> getAnimals() {
        return animalDao.findAll();
    }

    public void createAnimal(AnimalModel animal) {
        animalDao.save(animal);
    }

    public void deleteAnimal(Integer id) {
        animalDao.deleteById(id);
    }

    public void editAnimal(AnimalModel animal) {
        animalDao.save(animal); // Atualiza o animal existente
    }
}
