package ru.example.edu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PersonRegisterDto {
    private long id;
    private String name;
    private String username;
    private String password;
    @JsonProperty("department_name")
    private String departmentName;
    @JsonProperty("phone_number")
    private String phoneNumber;
}
