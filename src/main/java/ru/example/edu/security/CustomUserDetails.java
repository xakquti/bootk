package ru.example.edu.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.example.edu.entity.Person;
import java.util.Collection;


public record CustomUserDetails(Person person) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return person.getAuthorities();
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getUsername();
    }
}
