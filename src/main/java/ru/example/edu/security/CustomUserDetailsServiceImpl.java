package ru.example.edu.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.example.edu.exception.PersonNotFoundException;
import ru.example.edu.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository personRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByUsername(username).map(CustomUserDetails::new).orElseThrow(
                () -> new PersonNotFoundException("Person not found!")
        );
    }
}
