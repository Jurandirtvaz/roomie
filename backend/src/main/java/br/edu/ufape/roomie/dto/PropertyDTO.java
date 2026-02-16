package br.edu.ufape.roomie.dto;

import br.edu.ufape.roomie.enums.PropertyType;
import br.edu.ufape.roomie.enums.UserGender;
import lombok.Data;

@Data
public class PropertyDTO {
    String title;
    String description;
    PropertyType type;
    Double price;
    UserGender gender;
    Boolean acceptAnimals;

    // Campos do Address
    String street;
    String district;
    String number;
    String city;
    String state;
    String cep;

}
