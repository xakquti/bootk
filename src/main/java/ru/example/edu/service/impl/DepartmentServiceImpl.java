package ru.example.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.edu.dto.DepartmentDto;
import ru.example.edu.exception.DepartmentNotFoundException;
import ru.example.edu.repository.DepartmentRepository;
import ru.example.edu.service.DepartmentService;
import ru.example.edu.util.DepartmentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream().map(DepartmentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(DepartmentMapper::convertToDto).orElseThrow(
                () -> new DepartmentNotFoundException("Департамент не найден!!!")
        );
    }

    @Override
    public DepartmentDto getDepartmentByName(String name) {
        return departmentRepository.findByName(name).map(DepartmentMapper::convertToDto).orElseThrow(
                () -> new DepartmentNotFoundException("Департамент не найден!!!")
        );
    }
}
