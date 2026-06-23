package ru.example.edu.service;

import ru.example.edu.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartmentById(Long id);

    DepartmentDto getDepartmentByName(String name);
}
