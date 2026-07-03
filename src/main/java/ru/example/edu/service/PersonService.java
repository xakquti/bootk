package ru.example.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.example.edu.dto.*;

import javax.naming.AuthenticationException;
import java.util.List;

public interface PersonService {

    AuthDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    AuthDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;
    List<PersonDTO> getAllPersons();

    PersonDTO getPersonById(Long id);

    PersonDTO createPerson(PersonRegisterDto dto);

    PersonDTO updatePerson(Long id, PersonDTO dto);

    void deletePerson(Long id);

    PersonDTO getPersonByUserName(String username);

    Page<PersonDTO> getAllPersonPaginated(Pageable pageable);
    PersonDTO updateAvatar(Long id, MultipartFile multipartFile);
}
