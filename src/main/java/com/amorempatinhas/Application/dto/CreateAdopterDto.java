package com.amorempatinhas.Application.dto;

import com.amorempatinhas.Application.model.AnimalModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAdopterDto {
    private int id;

    private String name;
    private String cpf;
    private String email;
    private String phone;

}
