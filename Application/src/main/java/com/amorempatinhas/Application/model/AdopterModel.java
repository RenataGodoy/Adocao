package com.amorempatinhas.Application.model;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@XmlRootElement
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("AdopterModel")
public class AdopterModel {
    private int id;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    @JacksonXmlProperty(localName = "animalIds")
    @XmlElementWrapper(name = "animalIds")
    @XmlElement(name = "integer")
    private List<Integer> animalIds;
}
