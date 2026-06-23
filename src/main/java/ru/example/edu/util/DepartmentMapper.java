package ru.example.edu.util;

import lombok.experimental.UtilityClass;
import ru.example.edu.dto.DepartmentDto;
import ru.example.edu.dto.MeetingDTO;
import ru.example.edu.entity.Department;

@UtilityClass
public class DepartmentMapper {
    public DepartmentDto convertToDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto;
    }
}
