package ru.example.edu.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PersonRegisterDto {

    private String name;
    @JsonProperty("username")
    private String username;
    private String password;

    @JsonProperty("departmentName")
    private String departmentName;
    private String phoneNumber;
}
