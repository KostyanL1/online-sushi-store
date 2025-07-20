package com.legenkiy.service;


import com.legenkiy.model.Person;
import com.legenkiy.model.enums.Role;
import com.legenkiy.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)

public class PersonalDetailsServiceTest {

    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private  PersonalDetailsService personalDetailsService;

    @Test
    public void loadUserByUsername_withExistUser(){
        Person person = new Person(1,"Kostya", "3803562389230", Role.USER, true);

        Mockito.when(personRepository.findByCellNumber("3803562389230")).thenReturn(Optional.of(person));

        UserDetails userDetails = personalDetailsService.loadUserByUsername("3803562389230");
        Assertions.assertEquals(person.getCellNumber(), userDetails.getUsername());
        Assertions.assertEquals(person.getRole(), Role.USER);
    }
    @Test
    public void loadUserByUsername_withNotExistUser(){

        Mockito.when(personRepository.findByCellNumber("3803562389230")).thenReturn(Optional.empty());
        Assertions.assertThrows( UsernameNotFoundException.class, () -> personalDetailsService.loadUserByUsername("3803562389230"));
    }

}
