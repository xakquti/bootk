package ru.example.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.example.edu.dto.PersonDTO;
import ru.example.edu.dto.PersonRegisterDto;
import ru.example.edu.entity.Authority;
import ru.example.edu.entity.Department;
import ru.example.edu.entity.Person;
import ru.example.edu.exception.DepartmentNotFoundException;
import ru.example.edu.exception.PersonAlreadyExistsException;
import ru.example.edu.exception.PersonNotFoundException;
import ru.example.edu.repository.AuthorityRepository;
import ru.example.edu.repository.DepartmentRepository;
import ru.example.edu.repository.PersonRepository;
import ru.example.edu.service.PersonService;
import ru.example.edu.util.PersonMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final DepartmentRepository departmentRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(PersonMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPersonById(Long id) {
        return personRepository.findById(id).map(PersonMapper::convertToDto)
                .orElseThrow(() -> new PersonNotFoundException("Person not found!!!"));
    }

    @Override
    public PersonDTO createPerson(PersonRegisterDto dto) {

        if (personRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new PersonAlreadyExistsException("Email already exists!!!");
        }

        Optional<Authority> roleUser = authorityRepository.findByAuthority("ROLE_USER");
        if (roleUser.isEmpty()) {
            throw new RuntimeException("Authority not found");
        }

        Optional<Department> optionalDepartment = departmentRepository.findByName(dto.getDepartmentName());
        if(optionalDepartment.isEmpty()) {
            throw new DepartmentNotFoundException("Department not found!!!");
        }

        Person person = new Person();
        person.setName(dto.getName());
        person.setUsername(dto.getUsername());
        person.setDepartment(optionalDepartment.get());
        person.setAuthorities(Set.of(roleUser.get()));
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setPassword(passwordEncoder.encode(dto.getPassword()));

        return PersonMapper.convertToDto(personRepository.save(person));
    }

    @Override
    @Transactional
    public PersonDTO updatePerson(Long id, PersonDTO dto) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found!!!"));

        person.setName(dto.getName());
        person.setPhoneNumber(dto.getPhoneNumber());

        return PersonMapper.convertToDto(personRepository.save(person));
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public PersonDTO getPersonByUserName(String username) {
        Optional<Person> optionalPerson = personRepository.findByUsername(username);

        if (optionalPerson.isEmpty()) {
            throw new PersonNotFoundException("Person with username " + username + "not found!!!");
        }

        return PersonMapper.convertToDto(optionalPerson.get());
    }

    @Override
    public Page<PersonDTO> getAllPersonPaginated(Pageable pageable) {
        return personRepository.findAll(pageable).map(PersonMapper::convertToDto);
    }

    @Override
    public PersonDTO updateAvatar(Long id, MultipartFile multipartFile) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found!!!"));
        try {
            String originalFileName = multipartFile.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String name = UUID.randomUUID() + extension;

            Path path = Paths.get("uploads");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Path filePath = path.resolve(name);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            person.setPhotoUrl("/uploads/" + name);
            return PersonMapper.convertToDto(personRepository.save(person));
        } catch (IOException e){
            throw new RuntimeException("Could not save file", e);
        }
    }
}
