package com.amorempatinhas.Application.Dao;

import com.amorempatinhas.Application.model.AdopterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdopterDao extends JpaRepository<AdopterModel, Integer> {
    // JpaRepository já oferece métodos padrão como save, findById, findAll, deleteById
}
