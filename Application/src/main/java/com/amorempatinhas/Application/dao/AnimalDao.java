package com.amorempatinhas.Application.dao;

import com.amorempatinhas.Application.model.AnimalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalDao extends JpaRepository<AnimalModel, Integer> {
    // JpaRepository já oferece métodos padrão como save, findById, findAll, deleteById
}
