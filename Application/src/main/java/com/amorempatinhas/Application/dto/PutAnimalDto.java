package com.amorempatinhas.Application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutAnimalDto {
    private int id;
    private String name;
    private String species;
    private int age;
    private String breed;
    private int adopter_id;
}
