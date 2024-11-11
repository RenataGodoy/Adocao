package com.amorempatinhas.Application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutAdopterDto {
    private int id;

    private String name;
    private String cpf;
    private String email;
    private String phone;


}
