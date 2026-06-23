package ru.example.edu.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class PersonDTO {
    private long id;
    private String name;
    private String username;
    private String photoUrl;
    private String departmentName;
    private String phoneNumber;
}
