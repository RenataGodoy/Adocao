package com.amorempatinhas.Application.service;

import com.amorempatinhas.Application.dao.AdopterDao;
import com.amorempatinhas.Application.dao.AnimalDao;
import com.amorempatinhas.Application.dto.CreateAnimalDto;
import com.amorempatinhas.Application.dto.PutAnimalDto;

import com.amorempatinhas.Application.model.AdopterModel;
import com.amorempatinhas.Application.model.AnimalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalDao animalDao;
    private final AdopterDao adopterDao;

    @Autowired
    public AnimalService(AnimalDao animalDao, AdopterDao adopterDao) {
        this.animalDao = animalDao;
        this.adopterDao = adopterDao;
    }

    public AnimalModel getAnimal(final Integer id) {
        return animalDao.findById(id).orElse(null);
    }

    public List<AnimalModel> getAnimals() {
        return animalDao.findAll();
    }

    public void createAnimal(CreateAnimalDto animalDto) {
        Optional<AdopterModel> adopterModelOptional = adopterDao.findById(animalDto.getAdopter_id());

        AdopterModel adopter = null;

        if (adopterModelOptional.isPresent()) {
            adopter = adopterModelOptional.get();
        }

        AnimalModel animal = new AnimalModel();
        animal.setName(animalDto.getName());
        animal.setSpecies(animalDto.getSpecies());
        animal.setAge(animalDto.getAge());
        animal.setBreed(animalDto.getBreed());
        animal.setAdopter(adopter);

        animalDao.save(animal);
    }

    public void deleteAnimal(Integer id) {
        animalDao.deleteById(id);
    }

    public void editAnimal(PutAnimalDto animalDto) {
        // Busca o animal pelo ID correto (id do animal)
        Optional<AnimalModel> optionalAnimal = animalDao.findById(animalDto.getId());

        if (optionalAnimal.isPresent()) {
            AnimalModel animal = optionalAnimal.get();

            // Atualiza os campos do animal com os dados do DTO
            animal.setName(animalDto.getName());
            animal.setSpecies(animalDto.getSpecies());
            animal.setAge(animalDto.getAge());
            animal.setBreed(animalDto.getBreed());

            // Atualiza o adotante se o ID do adotante estiver presente e válido
            Optional<AdopterModel> adopterModelOptional = adopterDao.findById(animalDto.getAdopter_id());
            adopterModelOptional.ifPresent(animal::setAdopter);

            // Salva o animal atualizado no banco de dados
            animalDao.save(animal);
        } else {
            // Lança uma exceção se o animal não for encontrado
            throw new RuntimeException("Animal com id " + animalDto.getId() + " não encontrado.");
        }
    }

}
