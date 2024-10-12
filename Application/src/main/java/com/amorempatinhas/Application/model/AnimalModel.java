package com.amorempatinhas.Application.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalModel {
    private int id;
    private String name;
    private String species;
    private int age;
    private String breed;
    private int adopterId;
}
