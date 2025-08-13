package com.legenkiy.service.impl;

import com.legenkiy.dto.ProductTypeDto;
import com.legenkiy.exceptions.ObjectNotFoundException;
import com.legenkiy.model.ProductType;
import com.legenkiy.repository.ProductTypeRepository;
import com.legenkiy.service.api.ProductTypeService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductTypeServiceImpl  implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    @Override
    public List<ProductType> findAll() {
        return productTypeRepository.findAll();
    }

    @Override
    public void save(ProductTypeDto productTypeDto) {
        if (productTypeDto == null) throw new IllegalArgumentException("Type must not be null!");
        ProductType productType = new ProductType();
        productType.setDescription(productTypeDto.getDescription());
        productTypeRepository.save(productType);
    }

    @Override
    public void deleteById(int id) {
        if (productTypeRepository.existsById(id)){
            productTypeRepository.deleteById(id);
        }else throw new ObjectNotFoundException("Type not found!");

    }
}
