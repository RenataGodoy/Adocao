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
        // aqui eu to recebendo os dados da dto e instancio o animal ali em baixo pra criar no jpa
        //ai voce teria que criar um dto para cada rota de tipo post e put e colocar o que sera enviado nela e mudar os parametros dos requests, eh claro
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
            Optional<AnimalModel> optionalAnimal = animalDao.findById(animalDto.getAdopter_id());

            if (optionalAnimal.isPresent()) {
                AnimalModel animal = optionalAnimal.get();
                animal.setName(animalDto.getName());
                animal.setSpecies(animalDto.getSpecies());
                animal.setAge(animalDto.getAge());
                animal.setBreed(animalDto.getBreed());

                // Se for possível atualizar o adotante, busque e atualize assim:
                Optional<AdopterModel> adopterModelOptional = adopterDao.findById(animalDto.getAdopter_id());
                adopterModelOptional.ifPresent(animal::setAdopter);

                animalDao.save(animal);
            } else {
                throw new RuntimeException("Animal com id " + animalDao.findById(Integer.valueOf(animalDto.getAdopter_id() + " não encontrado.")));
            }
    }
}
