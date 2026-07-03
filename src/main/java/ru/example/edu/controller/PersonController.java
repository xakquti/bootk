package ru.example.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.example.edu.dto.PersonDTO;
import ru.example.edu.dto.PersonRegisterDto;
import ru.example.edu.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonRegisterDto personDTO) {
        System.out.println(personDTO);
        return ResponseEntity.ok(personService.createPerson(personDTO));
    }

    @GetMapping
    public List<PersonDTO> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @GetMapping("/profile")
    public ResponseEntity<PersonDTO> getCurrentPerson(Authentication authentication) {
        if (authentication == null && !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(personService.getPersonByUserName(authentication.getName()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @RequestBody PersonDTO dto) {
        return ResponseEntity.ok(personService.updatePerson(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<PersonDTO>> getAllPersonPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(personService.getAllPersonPaginated(pageable));
    }

    @PatchMapping("/{id}/avatar")
    public ResponseEntity<PersonDTO> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile multipartFile) {
        return ResponseEntity.ok(personService.updateAvatar(id, multipartFile));
    }
}
