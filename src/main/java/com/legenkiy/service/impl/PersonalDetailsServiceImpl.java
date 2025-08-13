package com.legenkiy.service.impl;


import com.legenkiy.model.Person;
import com.legenkiy.model.PersonalDetailsImpl;
import com.legenkiy.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;


    @Override
    public UserDetails loadUserByUsername(String cellNumber) throws UsernameNotFoundException {
        Person person = personRepository.findByCellNumber(cellNumber).orElseThrow(() -> new UsernameNotFoundException("User with this number not found!"));
        return new PersonalDetailsImpl(person);
    }


}
