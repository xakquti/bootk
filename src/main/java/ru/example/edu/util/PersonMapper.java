package ru.example.edu.util;

import lombok.experimental.UtilityClass;
import ru.example.edu.dto.PersonDTO;
import ru.example.edu.entity.Person;

@UtilityClass
public class PersonMapper {
    public PersonDTO convertToDto(Person person){
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setUsername(person.getUsername());
        personDTO.setPhotoUrl(person.getPhotoUrl());
        personDTO.setDepartmentName(person.getDepartment().getName());
        personDTO.setPhoneNumber(person.getPhoneNumber());
        return personDTO;
    }
}
