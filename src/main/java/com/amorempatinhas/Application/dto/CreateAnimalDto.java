package com.amorempatinhas.Application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAnimalDto {
    private String name;
    private String species;
    private int age;
    private String breed;
    private int adopter_id;
}
