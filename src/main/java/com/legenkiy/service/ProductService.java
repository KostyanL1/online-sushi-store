package com.legenkiy.service;


import com.legenkiy.model.Product;
import com.legenkiy.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final PersonRepository personRepository;


    public void save(Product product){

    }



}
